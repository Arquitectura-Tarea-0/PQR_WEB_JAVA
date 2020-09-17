package Modelo;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

public class Note {

    int id;
    String description, userId, requestId;

    public Note() {
    }

    //Mapeo de Json API a Modelo
    public void noteFromJson(final JSONObject responseAPI){
        try {
            //Conversion de string de la respuesta http en JSON con la respuesta de la API
            final JSONObject noteJson = new JSONObject(responseAPI.toString());
            
            //Mapeo del JSON en Modelo
            this.id          = Integer.valueOf(noteJson.get("id").toString());
            this.description = noteJson.get("description").toString();
            this.userId      = noteJson.get("user_id").toString();
            this.requestId   = noteJson.get("request_id").toString();
        } catch (JSONException ex) {
            Logger.getLogger(Note.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "Note [description=" + description + ", id=" + id + ", requestId=" + requestId + ", userId=" + userId
                + "]";
    }
    
}
