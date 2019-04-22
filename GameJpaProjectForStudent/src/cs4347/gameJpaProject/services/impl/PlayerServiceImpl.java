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
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import cs4347.gameJpaProject.entity.Player;
import cs4347.gameJpaProject.services.PlayerService;
import cs4347.gameJpaProject.util.DAOException;

public class PlayerServiceImpl implements PlayerService
{
    @PersistenceContext 
    private EntityManager em; 
    
    public PlayerServiceImpl(EntityManager em) {
        this.em = em;
    }
    
    // Provided as an example. 
    @Override
    public void create(Player player) throws DAOException, SQLException
    {
        try {
            em.getTransaction().begin();
            em.persist(player);
            em.getTransaction().commit();
        }
        catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public Player retrieve(Long playerID) throws DAOException, SQLException
    {

    	em.getTransaction().begin();
    	Player player = (Player)em.find(Player.class, playerID);
    	em.getTransaction().commit();

    	return player;
    }

    @Override
    public void update(Player p1) throws DAOException, SQLException
    {
    	try {
			em.getTransaction().begin();
			Player p2 = em.find(Player.class, p1.getId());
			p2.setFirstName(p1.getFirstName());
			p2.setLastName(p1.getLastName());
			p2.setJoinDate(p1.getJoinDate());
			p2.setEmail(p1.getEmail());
			p2.setCreditCards(p1.getCreditCards());
			em.getTransaction().commit();
		}
		catch (Exception ex) {
			em.getTransaction().rollback();
			throw ex;
		}
    }

    @Override
    public void delete(Long playerID) throws DAOException, SQLException
    {
    	try 
		{
			em.getTransaction().begin();
			Player p = em.find(Player.class, playerID);
			em.remove(p);
			
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
		Long player = (Long) em.createQuery("select COUNT(p.id) from Player as p")
				.getSingleResult();
		em.getTransaction().commit();
		return player;
    }

    @Override
    public int countCreditCardsForPlayer(Long playerID) throws DAOException, SQLException
    {
    	try
    		{
    		em.getTransaction().begin();
    		Player p1 = em.find(Player.class, playerID);
    		int count = p1.getCreditCards().size();
    		em.getTransaction().commit();
    		return count;
    		}
    	catch(Exception ex)
    	{
    		em.getTransaction().rollback();
    		throw ex;
    	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Player> retrieveByJoinDate(Date start, Date end) throws DAOException, SQLException
    {
    	try
    	{
    	em.getTransaction().begin();
		List<Player> player = (List<Player>)em.createQuery("from Player as p where p.joinDate between :dStart and :dEnd")
				.setParameter("dStart", start)
				.setParameter("dEnd", end)
				.getResultList();
		em.getTransaction().commit();
		return player;
    	}
    	catch(Exception ex)
    	{
    		throw ex;
    	}
    }
}
