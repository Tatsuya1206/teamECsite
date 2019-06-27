package com.internousdev.blue.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.blue.dto.ProductInfoDTO;
import com.internousdev.blue.util.DBConnector;

public class ProductInfoDAO {
	public List<ProductInfoDTO> getProductInfoListAll(){//商品一覧
	DBConnector dbc=new DBConnector();
	Connection con=dbc.getConnection();
	List<ProductInfoDTO> productInfoDTOList=new ArrayList<ProductInfoDTO>();
	String sql = "select * from product_info";

	try{
		PreparedStatement ps=con.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		while(rs.next()){
			ProductInfoDTO productInfoDTO=new ProductInfoDTO();
			productInfoDTO.setId(rs.getInt("id"));
			productInfoDTO.setProductId(rs.getInt("product_id"));
			productInfoDTO.setProductName(rs.getString("product_name"));
			productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
			productInfoDTO.setProductDescription(rs.getString("product_description"));
			productInfoDTO.setCategoryId(rs.getInt("category_id"));
			productInfoDTO.setPrice(rs.getInt("price"));
			productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
			productInfoDTO.setImageFileName(rs.getString("image_file_name"));
			productInfoDTO.setReleaseDate(rs.getDate("release_date"));
			productInfoDTO.setReleaseCompany(rs.getString("release_company"));
			productInfoDTOList.add(productInfoDTO);
		}
	}catch(SQLException e){
		e.printStackTrace();
	}
	finally{
		try{
			con.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	return productInfoDTOList;
}

	public ProductInfoDTO getProductInfoByProductId(int productId){//商品詳細画面
		DBConnector dbc=new DBConnector();
		Connection con=dbc.getConnection();
		ProductInfoDTO productInfoDTO=new ProductInfoDTO();
		String sql="select * from product_info where product_id = ?";

		try{
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, productId);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImageFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTO;
	}

	public List<ProductInfoDTO> getRelatedProductList(int categoryId,int productId,int limitOffset,int limitRowCount){//limitOffset データを取得する開始位置。limitRowCount データ取得件数。
		DBConnector dbc=new DBConnector();
		Connection con =dbc.getConnection();
		List<ProductInfoDTO> productInfoDTOList=new ArrayList<ProductInfoDTO>();
		String sql="select * from product_info where category_id = ? and product_id not in(?) "//同じカテゴリIDを持ち、既に表示されている詳細情報画面の商品の商品IDは含まない。
				+ "order by rand() limit ?,?";
		/*  rand():表示順をランダムにする。
		 *  limit 0,3:0番目から3件データを取得する。*/
		try{
			PreparedStatement ps=con.prepareStatement(sql);
			ps.setInt(1, categoryId);
			ps.setInt(2, productId);
			ps.setInt(3, limitOffset);
			ps.setInt(4, limitRowCount);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ProductInfoDTO productInfoDTO = new ProductInfoDTO();
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImageFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(productInfoDTO);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	public List<ProductInfoDTO> getProductInfoListByKeyword(String[] keywordsList){//キーワードだけでの検索。
		DBConnector dbc=new DBConnector();
		Connection con =dbc.getConnection();
		List<ProductInfoDTO> productInfoDTOList=new ArrayList<ProductInfoDTO>();
		String sql="select * from product_info";

		boolean initializeFlag=true;//initializeFlag=trueにすることでif文の条件判定をtrueにする。

		for(String keyword : keywordsList){

			if(!keyword.equals("")){//検索未入力の場合、もしくは検索時に①文字目が空白だった場合にfalse。

				if(initializeFlag){//if(true)の状態で１週目。２週目はif(false)。条件判定が必ずfalseになるのでelseの処理。
					sql +=" where (product_name like '%" + keyword + "%' or product_name_kana like '%" + keyword + "%')";//where句で絞込み。
					initializeFlag=false;//falseに変更をしないと２週目以降も当該sql文が結合して、where句がダブってsql文が誤った状態になる。
				}else{
					sql += " or (product_name like '%" + keyword + "%' or product_name_kana like '%" + keyword + "%')";//引数が２つ以上ならorで項目を増やす。
				}
			}
		}
		try{
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ProductInfoDTO productInfoDTO=new ProductInfoDTO();
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImageFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(productInfoDTO);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	public List<ProductInfoDTO> getProductInfoListByCategoryIdANDKeyword(String[] keywordsList,String categoryId){//カテゴリーとキーワードでの検索。
		DBConnector dbc=new DBConnector();
		Connection con=dbc.getConnection();
		List<ProductInfoDTO> productInfoDTOList=new ArrayList<ProductInfoDTO>();
		String sql="select * from product_info where category_id=" + categoryId;

		boolean initializeFlag=true;
		for(String keyword : keywordsList){
			if(!(keyword.equals("")) || keywordsList.length == 1){
				if(initializeFlag){
					sql += " and ((product_name like '%" + keyword + "%' or "
							+ "product_name_kana like '%" + keyword + "%')";
					initializeFlag=false;
				}else{
					sql +=" or (product_name like '%" + keyword + "%' or product_name_kana like '%" + keyword + "%')";
				}
			}
		}
		sql +=")";
		try{
			PreparedStatement ps=con.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				ProductInfoDTO productInfoDTO=new ProductInfoDTO();
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));
				productInfoDTO.setCategoryId(rs.getInt("category_id"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImageFileName(rs.getString("image_file_name"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(productInfoDTO);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				con.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

}