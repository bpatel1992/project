
package com.rahul.project.gateway.dto.enablex;


@lombok.Data
@SuppressWarnings("unused")
public class CreateRoomDTO {

    private com.rahul.project.gateway.dto.enablex.Data data;
    private String name;
    private String ownerRef;
    private Settings settings;
    private Sip sip;

}
