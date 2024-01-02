package com.sunbeam.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sunbeam.pojos.Candidate;

public class CandidateDaoImpl extends Dao implements CandidateDao {
	private PreparedStatement stmtFindAll;
	private PreparedStatement stmtFindAllOrderByVotesDesc;
	private PreparedStatement stmtFindByParty;
	private PreparedStatement stmtSave;
	private PreparedStatement stmtUpdate;
	private PreparedStatement stmtDeleteById;
	private PreparedStatement stmtFindById;
	private PreparedStatement stmtIncVotes;

	public CandidateDaoImpl() throws Exception {
		String sql = "SELECT * FROM candidates";
		stmtFindAll = con.prepareStatement(sql);
		sql = "SELECT * FROM candidates ORDER BY votes DESC";
		stmtFindAllOrderByVotesDesc = con.prepareStatement(sql);
		sql = "SELECT * FROM candidates WHERE party=?";
		stmtFindByParty = con.prepareStatement(sql);
		sql = "INSERT INTO candidates VALUES(default, ?, ?, ?)";
		stmtSave = con.prepareStatement(sql);
		sql = "UPDATE candidates SET name=?, party=?, votes=? WHERE id=?";
		stmtUpdate = con.prepareStatement(sql);
		sql = "DELETE FROM candidates WHERE id=?";
		stmtDeleteById = con.prepareStatement(sql);
		sql = "SELECT * FROM candidates WHERE id=?";
		stmtFindById = con.prepareStatement(sql);
		sql = "UPDATE candidates SET votes = votes + 1 WHERE id = ?";
		stmtIncVotes = con.prepareStatement(sql);
	}

	@Override
	public void close() throws Exception {
		stmtIncVotes.close();
		stmtFindById.close();
		stmtDeleteById.close();
		stmtUpdate.close();
		stmtSave.close();
		stmtFindByParty.close();
		stmtFindAllOrderByVotesDesc.close();
		stmtFindAll.close();
		super.close();
	}

	@Override
	public List<Candidate> findAll() throws Exception {
		List<Candidate> list = new ArrayList<Candidate>();
		ResultSet rs = stmtFindAll.executeQuery();
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String party = rs.getString("party");
			int votes = rs.getInt("votes");
			Candidate c = new Candidate(id, name, party, votes);
			list.add(c);
		}
		return list;
	}

	@Override
	public List<Candidate> findAllOrderByVotesDesc() throws Exception {
		List<Candidate> list = new ArrayList<Candidate>();
		ResultSet rs = stmtFindAllOrderByVotesDesc.executeQuery();
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			String party = rs.getString("party");
			int votes = rs.getInt("votes");
			Candidate c = new Candidate(id, name, party, votes);
			list.add(c);
		}
		return list;
	}
	
	@Override
	public List<Candidate> findByParty(String party) throws Exception {
		List<Candidate> list = new ArrayList<Candidate>();
		stmtFindByParty.setString(1, party);
		ResultSet rs = stmtFindByParty.executeQuery();
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			party = rs.getString("party");
			int votes = rs.getInt("votes");
			Candidate c = new Candidate(id, name, party, votes);
			list.add(c);
		}
		return list;		
	}
	
	@Override
	public int save(Candidate c) throws Exception {
		stmtSave.setString(1, c.getName());
		stmtSave.setString(2, c.getParty());
		stmtSave.setInt(3, c.getVotes());
		return stmtSave.executeUpdate();
	}
	
	@Override
	public int update(Candidate c) throws Exception {
		stmtUpdate.setString(1, c.getName());
		stmtUpdate.setString(2, c.getParty());
		stmtUpdate.setInt(3, c.getVotes());
		stmtUpdate.setInt(4, c.getId());
		return stmtUpdate.executeUpdate();
	}
	
	@Override
	public int deleteById(int id) throws Exception {
		stmtDeleteById.setInt(1, id);
		return stmtDeleteById.executeUpdate();
	}
	
	@Override
	public Optional<Candidate> findById(int id) throws Exception {
		stmtFindById.setInt(1, id);
		try(ResultSet rs = stmtFindById.executeQuery()) {
			if(rs.next()) {
				id = rs.getInt("id");
				String name = rs.getString("name");
				String party = rs.getString("party");
				int votes = rs.getInt("votes");
				Candidate c = new Candidate(id, name, party, votes);
				return Optional.of(c);
			}
		}
		return Optional.empty();
	}
	
	@Override
	public int incrementVotes(int candId) throws Exception {
		stmtIncVotes.setInt(1, candId);
		int cnt = stmtIncVotes.executeUpdate();
		return cnt;
	}
}




