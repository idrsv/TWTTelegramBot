package com.idrsv.TWT_MPEI_Bot.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//@Service
@AllArgsConstructor
//@Component
public class ConnectionService {

    private URL url;
    private HttpURLConnection httpConn;
//    private String path;


}
