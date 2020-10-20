
package com.rahul.project.gateway.dto.enablex.create.room.response;


@lombok.Data
@SuppressWarnings("unused")
public class Room {

    private String created;
    private com.rahul.project.gateway.dto.enablex.create.room.response.Data data;
    private String name;
    private String ownerRef;
    private String roomId;
    private Settings settings;
    private Sip sip;

}
