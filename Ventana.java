import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;

public class Panel extends JFrame {

    private JTree fileTree;

    public Panel() {
        super("Explorador de Archivos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setResizable(false);
        setLayout(new BorderLayout());

        // Crear el nodo raíz
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Documents");

        // Obtener la ruta de la carpeta "Documents" del usuario actual
        String documentsPath = System.getProperty("user.home") + File.separator + "Documents";
        File documentsDir = new File(documentsPath);

        // Verificar si la carpeta "Documents" existe
        if (documentsDir.exists() && documentsDir.isDirectory()) {
            // Agregar la carpeta "Documents" como nodo raíz
            rootNode.add(new DefaultMutableTreeNode(documentsPath));
            addFiles(documentsDir, rootNode);
        } else {
            JOptionPane.showMessageDialog(this, "La carpeta Documents no existe en este sistema.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // Crear el JTree con el nodo raíz
        fileTree = new JTree(rootNode);

        // Agregar el JTree a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(fileTree);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // Método recursivo para agregar archivos y carpetas como nodos hijos
    private void addFiles(File parent, DefaultMutableTreeNode parentNode) {
        File[] files = parent.listFiles();
        if (files != null) {
            for (File file : files) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(file.getName());
                parentNode.add(childNode);
                if (file.isDirectory()) {
                    addFiles(file, childNode);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Panel::new);
    }
}