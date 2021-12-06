package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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

	public Client getClientById(int cin) {
		Client c = em.find(Client.class, cin);
		return c;
	}

	public boolean addClient(Client c) {
		try {
			em.persist(c);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

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
	}

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

	}

	@Path("Del/{cin}")
	@DELETE
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response DeleteByCin(@PathParam(value = "cin") int cin) {
		try {
			Client c = em.find(Client.class, cin);
			if (c != null) {
				em.remove(c);
				return Response.ok().entity("Client:" + c.getCin() + "is deleted successfuly").build();
			} else
				return Response.ok().entity(cin + "Does not existe").build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@Path("UpdateC/{cin}")
	@PUT
	@Consumes(value = { MediaType.APPLICATION_JSON })
	public Response UpdateClient(@PathParam(value = "cin") int cin) {
		try {
			Client c = em.find(Client.class, cin);
			if (c != null) {
				c.setAdresse("69 Rue de Halo Eloued");
				return Response.ok().entity("Client:" + c.getCin() + " is updated succsesfuly").build();				
			}
			else
				return Response.ok().entity(cin + " Does not existe").build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}

	}

	@Path("AddClient")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddClient(Client c) {
		try {
			Client c2 = em.find(Client.class, c.getCin());
			if (c2 == null) {
				em.persist(c);
				return Response.ok().entity("Client successfuly added ").build();
			} else

				return Response.ok().entity("client:" + c2.getCin() + " already exist").build();
		}

		catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();

		}

	}

	@Path("Journal")
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getAlllogs() {
		List<Journal> logs = new ArrayList<Journal>();

		TypedQuery<Journal> query = em.createNamedQuery("Journal.findAll", Journal.class);
		logs = query.getResultList();

		if (logs.size() != 0)
			return Response.status(200).entity(logs).build();
		else
			return Response.status(200).entity("There is no saved logs in the DB").build();
	}
	@Path("logbyid")
	@GET
	@Produces(value = { MediaType.APPLICATION_JSON })
	public Response getlogByID(@QueryParam(value = "IDlog") int IDlog) {
		try {
			Journal j = em.find(Journal.class, IDlog);
			if (j != null)
				return Response.ok()
						.entity(j).build();
			else
				return Response.ok().entity(IDlog + " Does not existe").build();
		} catch (Exception e) {
			return Response.status(500).entity("Server Error!").build();
		}

	}

}
