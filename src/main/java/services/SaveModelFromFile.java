package services;

import model.TableDataColumns;
import java.io.*;

public class SaveModelFromFile {

    public boolean saveData(File f, TableDataColumns tdc){
        try(FileWriter writer = new FileWriter(f))
        {
            int i = 0;
            for(var t: tdc.getTitle()){
                writer.write("[" + t + "]\n");
                for(var d: tdc.getData()){
                    writer.write(String.valueOf(d.getData().get(i).get()) + "\n");
                }
                ++i;
            }
            writer.flush();
        }
        catch(IOException ex){
            return false;
        }
        return true;
    }

}
