package org.appShala.DriveService.Controller;

import jakarta.validation.Valid;
import org.appShala.DriveService.Payloads.DriveNodeCreationRequest;
import org.appShala.DriveService.Payloads.DriveNodeResponse;
import org.appShala.DriveService.Service.DriveNodeService;
import org.appShala.DriveService.Service.NodeTypeService;
import org.appShala.DriveService.Service.SharedNodeService;
import org.appShala.DriveService.Service.StarredNodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/nodes")
public class DriveController {
    private final DriveNodeService driveNodeService;
    private final NodeTypeService nodeTypeService;
    private final SharedNodeService sharedNodeService;
    private final StarredNodeService starredNodeService;

    public DriveController() {
        this.driveNodeService=new DriveNodeService();
        this.sharedNodeService=new SharedNodeService();
        this.starredNodeService=new StarredNodeService();
        this.nodeTypeService=new NodeTypeService();

    }
     @PostMapping("/createUser")
     public ResponseEntity<DriveNodeResponse> createUser(@Valid @RequestBody DriveNodeCreationRequest request , @RequestHeader ("UserID") UUID UserId){
         DriveNodeResponse newNode;
         newNode = driveNodeService.createNode(request, UserId);
         return new ResponseEntity<>(newNode, HttpStatus.CREATED);
     }
     @GetMapping("/{NodeId}")
    public ResponseEntity<DriveNodeResponse> getNodeDetails(@PathVariable UUID NodeId){

     }


}
