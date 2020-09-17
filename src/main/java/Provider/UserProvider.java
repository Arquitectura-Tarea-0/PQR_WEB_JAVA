package Provider;

import Modelo.User;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.*;

public class UserProvider extends HttpRequest {

	// Metodo para hacer login, en caso de exito almacena el usuario de lo contrarilo devuelve nulo
	public User login(User user) {
		try{
                    String respuesta = "";
                    Map<String, Object> params = new LinkedHashMap<>();
                    
                    // Parametros enviados por POST
                    params.put("password", 	user.getPasswordDigest());
                    params.put("email", 	user.getEmail());
                    
                    try{
                        respuesta = peticionHttpPost(super.url + "login", params);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    JSONObject responseAPI = new JSONObject(respuesta);
                    
                    if(responseAPI.has("user")){
                        user.userFromJson(new JSONObject(responseAPI.get("user").toString()), responseAPI.get("token").toString());
                        return user;
                    }
                    
                    return new User();
                }catch (JSONException ex) {
			Logger.getLogger(UserProvider.class.getName()).log(Level.SEVERE, null, ex);
		}
            return null;
	}

	// Metodo para crar usuarios, en caso de exito almacena el usario de lo contrario y por falta de documentación en la API lo devuelve nulo
	public User createUser(User user) throws JSONException{
		String respuesta = "";
		Map<String, Object> params = new LinkedHashMap<>();

		// Parametros enviados por POST
		params.put("name", 		user.getName());
		params.put("password", 	user.getPasswordDigest());
		params.put("email", 	user.getEmail());

		try{
			respuesta = peticionHttpPost(super.url + "users", params);
		}catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject responseAPI = new JSONObject(respuesta);

		if(responseAPI.has("user")){
			user.userFromJson(new JSONObject(responseAPI.get("user").toString()), responseAPI.get("token").toString());
			return user;
		}
		
		return new User();
	}

	//Obtiene un listado de todos los usuarios que hay en el sistema, no necesita autentificación para acceder a la petición
	public ArrayList<User> getUsers(){
		try{
                    ArrayList<User> users = new ArrayList<User>();
                    String respuesta = "";
                    
                    try{
                        respuesta = peticionHttpGet(super.url + "users");
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    JSONObject responseAPI = new JSONObject(respuesta);
                    
                    if(responseAPI.has("users")){
                        JSONArray arrayUsers = new JSONArray(responseAPI.get("users").toString());
                        for(int i=0; i<arrayUsers.length(); i++){
                            JSONObject userJson = new JSONObject(arrayUsers.get(i).toString());
                            User temp = new User();
                            temp.userFromJson(userJson, null);
                            users.add(temp);
                        }
                    }
                    
                    return users;
                }catch (JSONException ex) {
			Logger.getLogger(UserProvider.class.getName()).log(Level.SEVERE, null, ex);
		}
            return null;
	}

}
