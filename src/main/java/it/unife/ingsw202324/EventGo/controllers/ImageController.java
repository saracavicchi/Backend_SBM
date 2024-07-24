package it.unife.ingsw202324.EventGo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Value("${app.upload.dir}organizzatoriImg")
    private String organizzatoreDir;

}
