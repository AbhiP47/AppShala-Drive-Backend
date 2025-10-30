package org.appShala.DriveService.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DriveNode")
public class DriveNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

}
