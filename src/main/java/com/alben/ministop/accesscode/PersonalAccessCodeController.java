package com.alben.ministop.accesscode;

import org.springframework.web.bind.annotation.*;

@RestController
public class PersonalAccessCodeController {
    @PostMapping("/${ministop.web.rootPath}/code")
    public void requestCode() {

    }
}
