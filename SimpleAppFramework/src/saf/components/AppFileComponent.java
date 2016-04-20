package saf.components;

import java.io.IOException;
import saf.AppTemplate;

/**
 * This interface provides the structure for file components in
 * our applications. Note that by doing so we make it possible
 * for customly provided descendent classes to have their methods
 * called from this framework.
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public interface AppFileComponent {
    public void saveData(AppDataComponent data, String filePath) throws IOException;
    public void loadData(AppDataComponent data, String filePath, AppTemplate app) throws IOException;
    public void exportData(AppDataComponent data, String filePath) throws IOException;
    public void importData(AppDataComponent data, String filePath) throws IOException;
}
