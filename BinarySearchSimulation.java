import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BinarySearchSimulation extends JFrame {
    private int[] array;
    private int target;
    private int low, high, mid;
    private boolean found;
    private Timer timer;

    private JLabel targetLabel;
    private JPanel arrayPanel;
    private JPanel pointerPanel;

    public BinarySearchSimulation() {
        setTitle("Binary Search Simulation");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        array = generateSortedArray(15);
        target = array[new Random().nextInt(array.length)];
        low = 0;
        high = array.length - 1;
        found = false;

        JPanel topPanel = new JPanel();
        targetLabel = new JLabel("Target: " + target);
        topPanel.add(targetLabel);
        JButton startButton = new JButton("Start Simulation");
        startButton.addActionListener(e -> startSearchAnimation());
        topPanel.add(startButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel centeredPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0); 
        add(centeredPanel, BorderLayout.CENTER);

        pointerPanel = new JPanel(new GridLayout(1, array.length, 5, 5)); 
        gbc.gridy = 0;
        centeredPanel.add(pointerPanel, gbc);

 
        arrayPanel = new JPanel(new GridBagLayout());
        gbc.gridy = 1; 
        centeredPanel.add(arrayPanel, gbc);

        updateArrayDisplay();
        setVisible(true);
    }


    private int[] generateSortedArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        arr[0] = rand.nextInt(10);
        for (int i = 1; i < size; i++) {
            arr[i] = arr[i - 1] + rand.nextInt(10) + 1;
        }
        return arr;
    }


    private void updateArrayDisplay() {
        arrayPanel.removeAll();
        pointerPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); 

        for (int i = 0; i < array.length; i++) {
            JLabel arrayLabel = new JLabel(String.valueOf(array[i]), SwingConstants.CENTER);
            arrayLabel.setOpaque(true);
            arrayLabel.setPreferredSize(new Dimension(40, 40)); 
            arrayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            if (i == mid) {
                arrayLabel.setBackground(Color.YELLOW); 
            } else if (i == low) {
                arrayLabel.setBackground(Color.GREEN); 
            } else if (i == high) {
                arrayLabel.setBackground(Color.RED); 
            } else {
                arrayLabel.setBackground(Color.LIGHT_GRAY); 
            }

            gbc.gridx = i; 
            arrayPanel.add(arrayLabel, gbc);

         
            JLabel pointerLabel = new JLabel("", SwingConstants.CENTER);
            if (i == low) {
                pointerLabel.setText("↑ Low");
                pointerLabel.setForeground(Color.GREEN);
            } else if (i == mid) {
                pointerLabel.setText("↑ Mid");
                pointerLabel.setForeground(Color.YELLOW);
            } else if (i == high) {
                pointerLabel.setText("↑ High");
                pointerLabel.setForeground(Color.RED);
            }
            pointerPanel.add(pointerLabel);
        }
        pointerPanel.revalidate();
        pointerPanel.repaint();
        arrayPanel.revalidate();
        arrayPanel.repaint();
    }


    private void startSearchAnimation() {
        if (timer != null && timer.isRunning()) {
            timer.stop(); 
        }
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (found || low > high) {
                    JOptionPane.showMessageDialog(BinarySearchSimulation.this, found ? "Target found!" : "Target not found.");
                    timer.stop();
                    return;
                }

                mid = low + (high - low) / 2;
                if (array[mid] == target) {
                    found = true;
                } else if (array[mid] < target) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
                updateArrayDisplay();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BinarySearchSimulation::new);
    }
}
