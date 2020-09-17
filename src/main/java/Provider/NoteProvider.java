package Provider;

import Modelo.Note;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

public class NoteProvider extends HttpRequest{
    
    /**
     * Retorna las notas asociadas a un usuario independiente de a que PQR este asociada, aunque 
     * solo lo hace para el usuario logeado usando el token de ese usuario
    */
    public ArrayList<Note> getNotes(String userToken) {
        try {
            ArrayList<Note> notes = new ArrayList<Note>();
            String respuesta = "";
            
            try {
                respuesta = peticionHttpGetWithHeader(super.url + "notes/user_notes", userToken);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            
            JSONObject responseAPI = new JSONObject(respuesta);
            
            if (responseAPI.has("notes")) {
                JSONArray arrayNotes = new JSONArray(responseAPI.get("notes").toString());
                for (int i = 0; i < arrayNotes.length(); i++) {
                    JSONObject noteJson = new JSONObject(arrayNotes.get(i).toString());
                    Note temp = new Note();
                    temp.noteFromJson(noteJson);
                    notes.add(temp);
                }
            }
            
            return notes;
        } catch (Exception ex) {
            Logger.getLogger(NoteProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
    *  Retorna todas las notas asociadas a una request (independiente de que usuario
    *  las haya generado) pasando como parametro el id del request, también necesita
    *  el token del usario autenticado y retorna de ese modo todas las notas asociadas 
    *  al PQR(request)
    */
    public ArrayList<Note> getNotesByRequest(int requestId, String userToken) {
        try {
            ArrayList<Note> notes = new ArrayList<Note>();
            String respuesta = "";
            
            try {
                respuesta = peticionHttpGetWithHeader(super.url + "notes/get_notes/" + requestId, userToken);
            } catch (final Exception e) {
                e.printStackTrace();
            }
            
            JSONObject responseAPI = new JSONObject(respuesta);
            
            if (responseAPI.has("notes")) {
                JSONArray arrayNotes = new JSONArray(responseAPI.get("notes").toString());
                for (int i = 0; i < arrayNotes.length(); i++) {
                    JSONObject noteJson = new JSONObject(arrayNotes.get(i).toString());
                    Note temp = new Note();
                    temp.noteFromJson(noteJson);
                    notes.add(temp);
                }
            }
            
            return notes;
        } catch (JSONException ex) {
            Logger.getLogger(NoteProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Metodo que permite crear una nota, como parametros solicita, el id del request (PQR),
     * la descripcion o contenido de la nota y el token del usuario autenticado que esta
     * realizando la nota al request, finalmente retorna la nota creada con todos sus campos
     */
    public Note createNote(int requestId, String noteDescription, String userToken){
		try{
                    String respuesta = "";
                    Map<String, Object> params = new LinkedHashMap<>();
                    Note note = new Note();
                    
                    // Parametros enviados por POST
                    params.put("description", noteDescription);
                    
                    try{
                        respuesta = peticionHttpPostWithHeader(super.url + "notes/create/" + requestId, params, userToken);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    JSONObject responseAPI = new JSONObject(respuesta);
                    
                    if(responseAPI.has("note")){
                        note.noteFromJson(new JSONObject(responseAPI.get("note").toString()));
                    }
                    
                    return note;
                }catch (JSONException ex) {
			Logger.getLogger(NoteProvider.class.getName()).log(Level.SEVERE, null, ex);
		}
        return null;
    }

}
