package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Session Bean implementation class Gestionaire
 */
@Path("Services")
@Stateless
@LocalBean
public class Gestionaire {

	/**
	 * Default constructor.
	 */
	public Gestionaire() {
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "Banque")
	private EntityManager em;

	@Path("Clients")
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getAllClients() {
		List<Client> clients = new ArrayList<Client>();

		TypedQuery<Client> query = em.createNamedQuery("Client.findAll", Client.class);
		clients = query.getResultList();

		if (clients.size() != 0)
			return Response.status(200).entity(clients).build();
		else
			return Response.status(200).entity("There is no saved Clients in the DB").build();
	};

	@Path("ClientCin")
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getClientByID(@FormParam(value = "cin") int cin) {
		try {
			Client c = em.find(Client.class, cin);
			if (c != null)
				return Response.ok()
						.entity("---Client CIN---\n" + c.getCin() + "\n Name: " + c.getPrenom() + "\n Last name: "
								+ c.getNom() + "\n Phone number: " + c.getTel() + "\n Address:" + c.getAdresse())
						.build();
			else
				return Response.ok().entity(cin + " Does not existe").build();
		} catch (Exception e) {
			return Response.status(500).entity("Server Error!").build();
		}

	};

	@Path("Del/{cin}")
	@DELETE
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response DeleteByCin(@PathParam(value = "cin") int cin) {
		try {
			Client c = em.find(Client.class, cin);
			em.getTransaction().begin();
			em.remove(c);
			// em.getTransaction().commit();
			if (c != null)
				return Response.ok().entity("Client:" + c.getCin() + "is deleted successfuly").build();
			else
				return Response.ok().entity(cin + " Does not existe").build();
		} catch (Exception e) {
			return Response.status(500).entity("Server Error!").build();
		}

	};

	@Path("UpdateC")
	@PUT
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response UpdateClient(@FormParam(value = "cin") int cin) {
		try {
			Client c = em.find(Client.class, cin);
			em.getTransaction().begin();
			c.setPrenom("Majdi");
			c.setAdresse("{\"N°\":\"54\",\"Street\":\"Salah Ben Youssef\",\"ZipCode\":\"4180\",\"Cit\":\"Djerba\"}");
			c.setNom("Zlitni");
			c.setCin(13489415);
			c.setTel(54827345);
			em.getTransaction().commit();
			if (c != null)
				return Response.ok().entity("Client:" + c.getCin() + "is updated succsesfuly").build();
			else
				return Response.ok().entity(cin + " Does not existe").build();
		} catch (Exception e) {
			return Response.status(500).entity("Server Error!").build();
		}

	};

	@Path("Add")
	@POST
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response AddClient(@FormParam(value = "cin") int cin) {
		try {
			Client c2 = em.find(Client.class, cin);
			if(c2==null) {
				Client c = new Client(13256434,"Ben Foulen","Foulen",54672671,"{\"N°\":\"29\",\"Street\":\"Techno\",\"ZipCode\":\"5202\",\"Cit\":\"Borj Cedria\"}");
				em.getTransaction().begin();
				em.persist(c);
				em.getTransaction().commit();
				if (c != null)
					return Response.ok().entity("Client:" + c.getCin() + " added successfuly").build();
			}
			else
				return Response.ok().entity(" Client already existe").build();
		} catch (Exception e) {
			return Response.status(500).entity("Server Error!").build();
		}

	};

}
