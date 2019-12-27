package com.kumeo.traveltour.extras;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.kumeo.traveltour.model.Province;
import com.kumeo.traveltour.model.ServiceType;
import com.kumeo.traveltour.view.Activity.SplashActivity;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReadExcel {


    public static ArrayList<Province> readProvinceExcelFile(Context myContext){
        ArrayList<Province> listProvinces=new ArrayList<Province>();

        try {
            InputStream myInput;
            // initialize asset manager
            AssetManager assetManager = myContext.getAssets();
            //  open excel sheet
            myInput = assetManager.open("province.xls");
            // Create a POI File System object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // We now need something to iterate through the cells.
            Iterator<Row> rowIter = mySheet.rowIterator();
            int rowno =0;

            while (rowIter.hasNext()) {
                //Log.e(TAG, " row no "+ rowno );
                HSSFRow myRow = (HSSFRow) rowIter.next();
                if(rowno !=0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno =0;
                    long id=0;
                    String name="", code="";
                    Province province =new Province();
                    while (cellIter.hasNext()) {
                        HSSFCell myCell = (HSSFCell) cellIter.next();
                        if (colno==0){
                            id = myCell.getRowIndex();
                            province.setId(id);
                        }else if (colno==1){
                            name = myCell.toString();
                            province.setName(name);
                        }else if (colno==2){
                            code = myCell.toString();
                            province.setCode(code);
                        }
                        colno++;
                        //SplashActivity.appPreference.showToast( " id :" + myCell.getColumnIndex() + " -- " + myCell.toString());
                    }
                    listProvinces.add(province);
                    //SplashActivity.appPreference.showToast( id + " -- "+ name+ "  -- "+ code+"\n");
                }
                rowno++;
            }
            SplashActivity.appPreference.showToast(listProvinces.size()+"");
        } catch (Exception e) {
            Log.e("myTag", "error "+ e.toString());
        }

        return listProvinces;
    }
    public static List<String> getListProvincesName(Context myContext){
        List<String> listProvinces=new ArrayList<String>();

        try {
            InputStream myInput;
            // initialize asset manager
            AssetManager assetManager = myContext.getAssets();
            //  open excel sheet
            myInput = assetManager.open("province.xls");
            // Create a POI File System object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // We now need something to iterate through the cells.
            Iterator<Row> rowIter = mySheet.rowIterator();
            int rowno =0;

            while (rowIter.hasNext()) {
                //Log.e(TAG, " row no "+ rowno );
                HSSFRow myRow = (HSSFRow) rowIter.next();
                if(rowno !=0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno =0;
                    long id=0;
                    String name="", code="";

                    while (cellIter.hasNext()) {
                        HSSFCell myCell = (HSSFCell) cellIter.next();
                        if (colno==0){
                            id = myCell.getRowIndex();

                        }else if (colno==1){
                            name = myCell.toString();


                        }else if (colno==2){
                            code = myCell.toString();

                        }
                        colno++;
                        //SplashActivity.appPreference.showToast( " id :" + myCell.getColumnIndex() + " -- " + myCell.toString());
                    }
                    listProvinces.add(name);
                    //SplashActivity.appPreference.showToast( id + " -- "+ name+ "  -- "+ code+"\n");
                }
                rowno++;
            }
            SplashActivity.appPreference.showToast(listProvinces.size()+"");
        } catch (Exception e) {
            Log.e("myTag", "error "+ e.toString());
        }

        return listProvinces;
    }
    public static ArrayList<ServiceType> readServiceTypeExcelFile(Context myContext) {
        ArrayList<ServiceType> listService=new ArrayList<ServiceType>();

        try {
            InputStream myInput;
            // initialize asset manager
            AssetManager assetManager = myContext.getAssets();
            //  open excel sheet
            myInput = assetManager.open("service_type.xls");
            // Create a POI File System object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // We now need something to iterate through the cells.
            Iterator<Row> rowIter = mySheet.rowIterator();
            int rowno =0;

            while (rowIter.hasNext()) {
                //Log.e(TAG, " row no "+ rowno );
                HSSFRow myRow = (HSSFRow) rowIter.next();
                if(rowno !=0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno =0;
                    int id=0;
                    String service_name="";
                    ServiceType type=new ServiceType();
                    while (cellIter.hasNext()) {
                        HSSFCell myCell = (HSSFCell) cellIter.next();
                        if (colno==0){
                            id = myCell.getRowIndex();
                            type.setId(id);
                        }else if (colno==1){
                            service_name = myCell.toString();
                            type.setService_name(service_name);
                        }
                        colno++;
                        //SplashActivity.appPreference.showToast( " id :" + myCell.getColumnIndex() + " -- " + myCell.toString());
                    }
                    listService.add(type);
                    //SplashActivity.appPreference.showToast( id + " -- "+ service_name);
                    //SplashActivity.appPreference.showToast(listService.size()+"");
                }
                rowno++;
            }
        } catch (Exception e) {
            Log.e("myTag", "error "+ e.toString());
        }
        return listService;

    }

    public static List<String> getListServiceName(Context myContext) {
        List<String>ServiceName= new ArrayList<String>();

        try {
            InputStream myInput;
            // initialize asset manager
            AssetManager assetManager = myContext.getAssets();
            //  open excel sheet
            myInput = assetManager.open("service_type.xls");
            // Create a POI File System object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            // Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // We now need something to iterate through the cells.
            Iterator<Row> rowIter = mySheet.rowIterator();
            int rowno =0;

            while (rowIter.hasNext()) {
                //Log.e(TAG, " row no "+ rowno );
                HSSFRow myRow = (HSSFRow) rowIter.next();
                if(rowno !=0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    int colno =0;
                    int id=0;
                    String service_name="";

                    while (cellIter.hasNext()) {
                        HSSFCell myCell = (HSSFCell) cellIter.next();
                        if (colno==0){
                            id = myCell.getRowIndex();

                        }else if (colno==1){
                            service_name = myCell.toString();

                        }
                        colno++;
                        //SplashActivity.appPreference.showToast( " id :" + myCell.getColumnIndex() + " -- " + myCell.toString());
                    }
                    ServiceName.add(service_name);
                    //SplashActivity.appPreference.showToast( id + " -- "+ service_name);
                    //SplashActivity.appPreference.showToast(listService.size()+"");
                }
                rowno++;
            }
        } catch (Exception e) {
            Log.e("myTag", "error "+ e.toString());
        }
        return ServiceName;

    }
}
