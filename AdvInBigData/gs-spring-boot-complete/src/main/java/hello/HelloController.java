package hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.condition.ConsumesRequestCondition;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.sun.javafx.collections.MappingChange.Map;

import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class HelloController {

	@Autowired
	MessagePublisher messagePublisher;

	@RequestMapping(value = "/plan/{keyID}", method = RequestMethod.GET , headers = "Accept=application/json")
	public Object indexGet(@PathVariable String keyID , HttpServletRequest request , HttpServletResponse response) {

		String authCode[] = request.getHeader("Authorization").split(" ");
		String access;
		try {
			access = validateToken(authCode[1],"GET");
			if (access.contains(","))
			{
				response.setStatus(401);
				return "Not Authorized to make a GET request";
			}
			else if(access.contains("false"))
			{
				response.setStatus(403);
				return "Invalid Token!!";
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}




		Jedis jedis = new Jedis();
		JSONObject jsonObject = null;

		JSONParser jsonParser = new JSONParser();
		try {
			Object obj = jsonParser.parse(jedis.get(keyID));
			jsonObject = (JSONObject) obj;
		} catch (ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return jsonObject;
		}





		return jsonObject;


	}

	@RequestMapping(value = "/plan", method = RequestMethod.POST , consumes = "application/json")
	public String parseJson(@RequestHeader HttpHeaders headers ,@RequestBody String entity) throws ParseException
	{
		System.out.println(entity);   	


		JSONParser jsonParser = new JSONParser();
		UUID idOne = UUID.randomUUID();
		Object object;
		try {
			object = jsonParser.parse(entity);
			JSONObject jsonObject = (JSONObject) object;
			//		         System.out.println(jsonObject);

			Jedis jedis = new Jedis();
			String newnew= jsonObject.toString();

			try {
				if (ValidationUtils.isJsonValid(jedis.get("schema"), entity))
				{

					System.out.println("Valid!");

					jedis.set(idOne.toString(), newnew);
					//						messagePublisher.publish(newnew);

					StringBuilder sb = new StringBuilder(idOne.toString());
					sb.append("@ID");
					sb.append(newnew);			  

					messagePublisher.publish(sb.toString());


				}else{
					System.out.println("NOT valid!");
					return "Not Valid";
				}
			} catch (ProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 




		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return idOne.toString();
	}

	@RequestMapping(value = "/plan/{keyID}", method = RequestMethod.DELETE)
	public String indexDelete(@PathVariable String keyID) {
		Jedis jedis = new Jedis();
		try{
			jedis.del(keyID);
		}
		catch(Exception e)
		{
			return "Not Found";
		}
		return "DELETED JSON!";
	}


	@RequestMapping(value = "/plan/{keyID}/{key}", method = RequestMethod.DELETE)
	public JSONObject indexDeleteKey(@PathVariable String keyID , @PathVariable String key) {

		Jedis jedis = new Jedis();
		JSONObject jsonObject = null;

		JSONParser jsonParser = new JSONParser();
		try {
			Object obj = jsonParser.parse(jedis.get(keyID));
			jsonObject = (JSONObject) obj;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(key);

		jsonObject.remove(key);



		String newnew= jsonObject.toString();



		return jsonObject;


	}



	@RequestMapping(value = "/plan/{keyID}", method = RequestMethod.PUT,  consumes = "application/json")
	public Object indexPut(@PathVariable String keyID, @RequestHeader HttpHeaders headers ,@RequestBody JSONObject  entity) throws IOException {

		Jedis jedis = new Jedis();
		JSONObject jsonObject = null;

		JSONParser jsonParser = new JSONParser();
		try {
			Object obj = jsonParser.parse(jedis.get(keyID));
			jsonObject = (JSONObject) obj;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(entity);
		Set<String> keys = entity.keySet();
		for (String key : keys) {
			jsonObject.put(key, entity.get(key));

		}

		String newnew= jsonObject.toString();

		try {
			if (ValidationUtils.isJsonValid(jedis.get("schema"), newnew))
			{

				System.out.println("Valid!");

				jedis.set(keyID, newnew);
				//				  messagePublisher.publish(keyID);
				StringBuilder sb = new StringBuilder(keyID);
				sb.append("@ID");
				sb.append(newnew);			  

				messagePublisher.publish(sb.toString());

				return jsonObject;
			}else{
				System.out.println("NOT valid!");
				return "Not Valid";
			}
		} catch (ProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




		jedis.set(keyID, newnew);

		return jsonObject;
	}

	@RequestMapping(value = "/schema_save", method = RequestMethod.POST , consumes = "application/json")
	public ResponseEntity saveSchema (@RequestHeader HttpHeaders headers ,@RequestBody String entity) throws ParseException
	{
		System.out.println(entity);

		JSONParser jsonParser = new JSONParser();
		Object object;
		try {
			object = jsonParser.parse(entity);
			JSONObject jsonObject = (JSONObject) object;
			System.out.println(jsonObject);

			Jedis jedis = new Jedis();
			String newnew= jsonObject.toString();
			jedis.set("schema", newnew);


		} catch (org.json.simple.parser.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		return null;
	}

	public String validateToken(String token, String method) throws Exception{
		boolean flag = true;
		JSONArray roles = null ;
		try {
			String json = GenerateToken.decrypt(token);
			JSONParser jsonParser = new JSONParser();
			Object obj = jsonParser.parse(json);
			JSONObject jsonObject = (JSONObject) obj;
			roles = (JSONArray) jsonObject.get("roles");
		}
		catch(Exception e){
			flag = false ;
			return "false";
		}
		if(flag == true){
			if(roles != null && roles.contains(method))
			{
				return "true";
			}
			else{
				return "Denied Access";
			}
		}else
		{
			return "false";    	
		}

	}

}
