package services;

import javafx.beans.property.SimpleDoubleProperty;
import model.TableData;
import model.TableDataColumns;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class LoadModelFromFile {

    public TableDataColumns loadData(File f){
        ArrayList<String> title = new ArrayList<>();
        ArrayList<ArrayList<Double>> values = new ArrayList<>();
        TableDataColumns tdc = new TableDataColumns();

        try {
            FileReader fr = new FileReader(f);
            BufferedReader reader = new BufferedReader(fr);
            String line = "";
            while (true) {
                line = reader.readLine();
                if(line == null){
                    break;
                }
                if(line.matches("\\s*\\[[\\w\\W0-9\\s]+\\]\\s*")){ // title
                    line = line.trim();
                    line = line.substring(1, line.length() - 1);
                    title.add(line);
                    values.add(new ArrayList<>());
                }else{ // data
                    line = line.replace(',', '.');
                    Double result;
                    try{
                        result = Double.valueOf(line);
                    }catch(NumberFormatException e){
                        return null;
                    }
                    if(result != null){
                        values.get(values.size() - 1).add(result);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int bigSize = values.stream().mapToInt(n -> n.size()).max().orElseThrow(NoSuchElementException::new);
        for(var arr: values){
            arr.addAll(Collections.nCopies(bigSize - arr.size(), 0.0));
        }
        for (int i = 0; i < bigSize; ++i) {
            tdc.getData().add(new TableData());
        }
        for(int j = 0; j < bigSize; ++j) {
            for (int i = 0; i < values.size(); ++i) {
                var val = values.get(i).get(j);
                tdc.getData().get(j).getData().add(new SimpleDoubleProperty(val));
            }
        }
        tdc.getTitle().addAll(title);
        return tdc;
    }

}
