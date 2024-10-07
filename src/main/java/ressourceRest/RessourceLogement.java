package ressourceRest;

import entities.Logement;
import metiers.LogementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;//make sure that this is the import path for Response
import java.util.ArrayList;
import java.util.List;

@Path("/logements")
public class RessourceLogement {
    public static LogementBusiness logementB = new LogementBusiness();
    @POST
    @Consumes ("application/xml")
    @Produces("text/plain")
    public Response ajouterLogement( Logement l){
       if( logementB.addLogement(l))
              return Response.status(Response.Status.CREATED).entity("Logement ajouté avec succés").build();
         else
              return Response.status(Response.Status.NOT_FOUND).entity("Logement non ajouté").build();
    }


@GET
@Produces ("application/json")
public Response getLogementsByDeleguation (@QueryParam(value ="delegation") String delegation,@QueryParam(value="ref") String ref){
        List<Logement> list = new ArrayList<Logement>();
        if(delegation==null && ref==null)
        {
            list=logementB.getLogements();
        }
        if (delegation!=null) {
            list=logementB.getLogementsByDeleguation(delegation);
        }  if (ref!=null) {
            list=logementB.getLogementsListeByref(Integer.parseInt(ref));
        }
    if(list.size()!=0)
            return Response.status(Response.Status.OK).entity(list).build();
        else
            return Response.status(Response.Status.NO_CONTENT).entity("Aucun logement trouvé").build();

}
@DELETE
@Produces("text/plain")
@Path("{id}")
public Response supprimerLogement(@PathParam("id") int reference){
        if (logementB.deleteLogement(reference))
            return Response.status(Response.Status.OK).entity("logement deleted").build();
        else
            return Response.status(Response.Status.NOT_FOUND).entity("not deleted").build();

}
@PUT
@Consumes("application/xml")
@Produces("text/plain")
@Path("{ref}")
public Response modifierLogement(@PathParam("ref") int reference, Logement l ){
    if( logementB.updateLogement(reference, l))
        return Response.status(Response.Status.OK).entity("Logement updated avec succés").build();
    else
        return Response.status(Response.Status.NOT_FOUND).entity("Logement non ajouté").build();
}
}