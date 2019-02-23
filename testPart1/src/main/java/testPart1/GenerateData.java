package testPart1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import testPart1.model.Office;

public class GenerateData {
	private List<Office> off = new ArrayList<>();
	private Integer countOperation;
	
	private boolean addOffices(String fname){
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fname)))){
            String line;
            while ((line = reader.readLine()) != null) {
    			Office o = new Office();
    			o.setCode(Integer.valueOf(line.split(" ")[0]));
    			o.setDescription(line.split(" ")[1]);
    			off.add(o);
            }
		} catch(IOException | NumberFormatException ex){
			System.out.println("Error reading Offices from file!");
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean setOpsCount(String count){
		try{
			countOperation = Integer.valueOf(count);
		}catch(NumberFormatException ex){
			System.out.println("Error parsing args[1] to Integer!");
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void process(List<String> outputFiles){
		System.out.println(outputFiles);
	}
	
	public static void main(String[] args){
		if(args.length<3){
			System.out.println("Arguments absent!");
		}
		GenerateData gd = new GenerateData();
		if(gd.addOffices(args[0])&&gd.setOpsCount(args[1])){
			gd.process(Arrays.asList(Arrays.copyOfRange(args,2,args.length)));
		}
	}

	public List<Office> getOffices() {
		return off;
	}
}
