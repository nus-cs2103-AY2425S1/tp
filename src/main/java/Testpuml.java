import java.io.File;
import java.io.IOException;
import java.util.List;

import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;

class Testpuml {
    public static void main(String[] args) {
        try {
            File source = new File("./docs/diagrams/ArchitectureDiagram.puml");
            System.out.println("Current working directory: " + System.getProperty("user.dir"));
            if (!source.exists()) {
                System.out.println("puml file not found.");
                return;
            }
            SourceFileReader reader = new SourceFileReader(source);
            List<GeneratedImage> list = reader.getGeneratedImages();
            // Generated files
            if (list.isEmpty()) {
                System.out.println("No images generated from the .puml file.");
                return;
            }
            File png = list.get(0).getPngFile();
            if (png == null) {
                System.out.println("Failed to generate PNG file.");
            }
        } catch (IOException e) {
            System.out.println("Failed to open file.");
            return;
        }
    }
}

