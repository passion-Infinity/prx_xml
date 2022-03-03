/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.NewsDTO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC
 */
public class NewsDAO {

    private static final String CONNECTION_STRING = "jdbc:sqlserver://localhost:1433;databaseName=SE1412_PRX_Sinhvien";
    private static final String USER = "sa";
    private static final String PASS = "admin123";

    public NewsDAO() {
    }

    public boolean insertNews(NewsDTO dto) throws Exception {
        boolean check = false;
        String sql = "Insert into tbl_VnExpress (Title, Description, Link, pubDate) values(?,?,?,?)";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASS);
                PreparedStatement preStm = conn.prepareStatement(sql)) {
            preStm.setString(1, dto.getTitle());
            preStm.setString(2, dto.getDescription());
            preStm.setString(3, dto.getLink());
            preStm.setString(4, dto.getPubDate());
            check = preStm.executeUpdate() > 0;
            return check;
        }
    }

    public List<NewsDTO> findByLikeTitle(String search) throws Exception {
        List<NewsDTO> result = null;
        String title, desc, pubDate, link;
        int id;
        NewsDTO dto = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String sql = "Select Id, Title, Description, pubDate, Link "
                + "From tbl_VnExpress "
                + "Where Title Like ?";
        try (Connection conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASS);
                PreparedStatement preStm = conn.prepareStatement(sql)) {
            preStm.setString(1, "%" + search + "%");
            try (ResultSet rs = preStm.executeQuery()) {
                result = new ArrayList<>();
                while (rs.next()) {
                    id = rs.getInt("Id");
                    title = rs.getString("Title");
                    desc = rs.getString("Description");
                    link = rs.getString("Link");
                    pubDate = rs.getString("pubDate");
                    dto = new NewsDTO(title, desc, link, pubDate, id);
                    result.add(dto);
                }
            }
        }
        return result;
    }
}
