package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.StulistBean;

// 入力機能(search)
public class StulistDAO extends DAO {
public void insert(StulistBean Sl) throws Exception {

        try (Connection con = getConnection();
            PreparedStatement st = con.prepareStatement(
                "INSERT INTO student (id, name, className) VALUES (?, ?, ?)")) {

                st.setInt(1, Sl.getId());
                st.setString(2, Sl.getName());
                st.setString(3, Sl.getClassName());


                st.executeUpdate();
        }

	}

// 削除機能(delete)
public int deleteByName(String name) throws Exception {
    int count = 0;

    // 書籍名だけですが、著者名でも実行できるようにしたい

    String sql = "DELETE FROM student WHERE id = ?";

    try (Connection con = getConnection();
         PreparedStatement st = con.prepareStatement(sql)) {

        st.setString(1, name);
        count = st.executeUpdate();
    }

    return count;
}

// 検索機能(select)
public List<StulistBean> search(String keyword) throws Exception {
    List<StulistBean> list = new ArrayList<>();

    try (Connection con = getConnection();
         PreparedStatement st = con.prepareStatement(
             "SELECT * FROM booklist WHERE name LIKE ? OR author LIKE ?")) {

        String likeKeyword = "%" + keyword + "%";
        st.setString(1, likeKeyword);
        st.setString(2, likeKeyword);

        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            StulistBean Sl = new StulistBean();
            Sl.setId(rs.getInt("id"));
            Sl.setName(rs.getString("name"));
            Sl.setClassName(rs.getString("className"));
            list.add(Sl);
        }
    }
    return list;
}

}
