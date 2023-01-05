package com.idrsv.TWT_MPEI_Bot.service;

import com.idrsv.TWT_MPEI_Bot.constants.PathEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@Component
public class TWTService {
    private HttpURLConnection httpConn;
    private String html;
    private final ArrayList<String> imageByTemperature = new ArrayList<>();
    private final ArrayList<String> imageByPressure = new ArrayList<>();
    private LinkedHashMap<String, String> mapByTemperature = new LinkedHashMap<>();
    private LinkedHashMap<String, String> mapByPressure = new LinkedHashMap<>();

    {
        mapByTemperature.put("P_s_max", null);
        mapByTemperature.put("P_s", null);
        mapByTemperature.put("P_s_delta", null);
        mapByTemperature.put("P_s_min", null);
        mapByTemperature.put("v'", null);
        mapByTemperature.put("v\"", null);
        mapByTemperature.put("p'", null);
        mapByTemperature.put("p\"", null);
        mapByTemperature.put("h'", null);
        mapByTemperature.put("h\"", null);
        mapByTemperature.put("r", null);
        mapByTemperature.put("S'", null);
        mapByTemperature.put("S\"", null);
        mapByTemperature.put("S\"-S'", null);

        mapByPressure.put("T_s_max", null);
        mapByPressure.put("T_s", null);
        mapByPressure.put("T_s_min", null);
        mapByPressure.put("v'", null);
        mapByPressure.put("v\"", null);
        mapByPressure.put("p'", null);
        mapByPressure.put("p\"", null);
        mapByPressure.put("h'", null);
        mapByPressure.put("h\"", null);
        mapByPressure.put("r", null);
        mapByPressure.put("S'", null);
        mapByPressure.put("S\"", null);
        mapByPressure.put("S\"-S'", null);

    }

    public Map<String, String> inAStateOfSaturationByTemperature(int temperature) throws IOException {
        connection(PathEnum.PATH_1.getPath());
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        //1029-Temp C-0,K-1,F-2,R-3    232=T
        writer.write("scrollX=0&scrollY=0&232=" + temperature + "&1029=0&948=6&949=2");
        response(writer, httpConn);
        Document doc = Jsoup.parse(html);
        Elements pngs = doc.select("img[src$=.png]");
        int iter = 1;
        for (Element el : pngs) {
            String link = el.attr("src");
            if (link.startsWith("/MCS/") && iter > 4 && iter < 19) {
                imageByTemperature.add("http://twt.mpei.ru" + link);
            }
            iter++;
        }
        return creatingMap(imageByTemperature, mapByTemperature);
    }

    public Map<String, String> inAStateOfSaturationByPressure() throws IOException {
        connection(PathEnum.PATH_2.getPath());
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        //1179-0 МПа
        writer.write("scrollX=0&scrollY=0&844=1%2C01&1179=0&1180=6&1181=2");
        response(writer, httpConn);
        Document doc = Jsoup.parse(html);
        Elements pngs = doc.select("img[src$=.png]");
        int iter = 1;
        for (Element el : pngs) {
            String link = el.attr("src");
            if (link.startsWith("/MCS/") && iter > 4 && iter < 19) {
                imageByPressure.add("http://twt.mpei.ru" + link);
            }
            iter++;
        }
        return creatingMap(imageByPressure, mapByPressure);
    }

    public Map<String, String> creatingMap(ArrayList<String> list, LinkedHashMap<String, String> map) {
        int i = 0;
        for (String key : map.keySet()) {
            map.put(key, list.get(i));
            i++;
            if (i == 14) {
                break;
            }
        }
        return map;
    }


//    public void dependingOnTemperatureAndPressure(double temperature, double pressure) throws IOException {
//        connection(path3);
//        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
//        //1403-T в C, 1417-P в МПа
//        writer.write("scrollX=0&scrollY=0&1403=" + temperature + "&1414=0&1417=" + pressure + "&1418=0&1281=3&1282=6");
//        response(writer, httpConn);
//
//    }

    public void response(OutputStreamWriter writer, HttpURLConnection httpConn) throws IOException {
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();
        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        html = s.hasNext() ? s.next() : "";
    }

    public void connection(String path) {
        URL url;
        try {
            url = new URL(path);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            String propertiesFilename = "src/main/resources/request.properties";
            List<String> lines = Files.readAllLines(Paths.get(propertiesFilename), StandardCharsets.UTF_8);
            for (String line : lines) {
                String[] arg = line.split(":");
                httpConn.setRequestProperty(arg[0], arg[1]);
            }
            httpConn.setRequestProperty("Referer", path);
            httpConn.setDoOutput(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Загрузка фотографий в папку
//    private static void getImage(String src) throws IOException {
//        String destinationDirectory = "/Users/idrsv/Desktop/photos/";
//        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destinationDirectory + src.substring(src.lastIndexOf("/"))))) {
//            URL url = new URL(src);
//            InputStream inputStream = url.openStream();
//            for (int i; (i = inputStream.read()) != -1; ) {
//                outputStream.write(i);
//            }
//        }
//    }
}