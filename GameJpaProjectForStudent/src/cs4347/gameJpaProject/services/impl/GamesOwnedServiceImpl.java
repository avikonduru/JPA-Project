/* NOTICE: All materials provided by this project, and materials derived 
 * from the project, are the property of the University of Texas. 
 * Project materials, or those derived from the materials, cannot be placed 
 * into publicly accessible locations on the web. Project materials cannot 
 * be shared with other project teams. Making project materials publicly 
 * accessible, or sharing with other project teams will result in the 
 * failure of the team responsible and any team that uses the shared materials. 
 * Sharing project materials or using shared materials will also result 
 * in the reporting of all team members for academic dishonesty. 
 */
package cs4347.gameJpaProject.services.impl;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cs4347.gameJpaProject.entity.Game;
import cs4347.gameJpaProject.entity.GamesOwned;
import cs4347.gameJpaProject.services.GamesOwnedService;
import cs4347.gameJpaProject.util.DAOException;

public class GamesOwnedServiceImpl implements GamesOwnedService
{
    @PersistenceContext 
    private EntityManager em; 
    
    public GamesOwnedServiceImpl(EntityManager em) {
        this.em = em;
    }
    
    @Override
    public void create(GamesOwned gamesOwned) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			em.persist(gamesOwned);
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public GamesOwned retrieveByID(Long gamesOwnedID) throws DAOException, SQLException
    {
        em.getTransaction().begin();
        GamesOwned go = (GamesOwned)em.find(GamesOwned.class, gamesOwnedID);
        em.getTransaction().commit();
        	
        return go;
    }

    @Override
    public GamesOwned retrievePlayerGameID(Long playerID, Long gameID) throws DAOException, SQLException
    {
    	try 
		{
			em.getTransaction().begin();
			List<GamesOwned> gamesOwned = (List<GamesOwned>)em.createQuery("from GamesOwned as go1 where go1.player.id = :player and go1.game.id = :game")
					.setParameter("player", playerID)
					.setParameter("game", gameID)
					.getResultList();
			em.getTransaction().commit();
			
			if(gamesOwned == null) {
				throw new DAOException("Game ID or Player ID Not Found ");
			}
			
			return gamesOwned.get(0);
				
		}
		catch (Exception ex) 
		{
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public List<GamesOwned> retrieveByGame(Long gameID) throws DAOException, SQLException
    {
    	try 
		{
			em.getTransaction().begin();
			List<GamesOwned> gamesOwned = (List<GamesOwned>)em.createQuery("from GamesOwned as go1 where go1.game.id = :game")
					.setParameter("game", gameID)
					.getResultList();
			em.getTransaction().commit();
			
			return gamesOwned;
				
		}
		catch (Exception ex) 
		{
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public List<GamesOwned> retrieveByPlayer(Long playerID) throws DAOException, SQLException
    {
    	try 
		{
			em.getTransaction().begin();
			List<GamesOwned> gamesOwned = (List<GamesOwned>)em.createQuery("from GamesOwned as go1 where go1.player.id = :player")
					.setParameter("player", playerID)
					.getResultList();
			em.getTransaction().commit();
			
			return gamesOwned;
		}
		catch (Exception ex) 
		{
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public void update(GamesOwned go1) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			GamesOwned go2 = em.find(GamesOwned.class, go1.getId());
			go2.setPlayer(go1.getPlayer());
			go2.setGame(go1.getGame());
			go2.setPurchaseDate(go1.getPurchaseDate());
			go2.setPurchasePrice(go1.getPurchasePrice());
			
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public void delete(Long gamesOwnedID) throws DAOException, SQLException
    {
    	try 
		{
			em.getTransaction().begin();
			GamesOwned cust = em.find(GamesOwned.class, gamesOwnedID);
			em.remove(cust);
			
			em.getTransaction().commit();
		}
		catch (Exception ex) 
		{
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public Long count() throws DAOException, SQLException
    {
    	em.getTransaction().begin();
		Long game = (Long) em.createQuery("select COUNT(go.id) from GamesOwned as go")
				.getSingleResult();
		em.getTransaction().commit();
		return game;
    }

}
