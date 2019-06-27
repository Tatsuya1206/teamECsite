package com.internousdev.blue.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.blue.dao.MCategoryDAO;
import com.internousdev.blue.dao.ProductInfoDAO;
import com.internousdev.blue.dto.MCategoryDTO;
import com.internousdev.blue.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class ProductListAction extends ActionSupport implements SessionAware {
	private List<ProductInfoDTO> productInfoDTOList;
	private Map<String,Object> session;

	public String execute(){
		ProductInfoDAO productInfoDAO=new ProductInfoDAO();
		productInfoDTOList=productInfoDAO.getProductInfoListAll();

		//カテゴリーリストが表示されていなければ、カテゴリーリストを作成する。
		if(!session.containsKey("mCategoryDTOList")){
			List<MCategoryDTO> mCategoryDTOList=new ArrayList<MCategoryDTO>();
			MCategoryDAO mCategoryDAO=new MCategoryDAO();
			try{
				mCategoryDTOList=mCategoryDAO.getMCategoryList();
			}catch(NullPointerException e){
				mCategoryDTOList=null;
			}
			session.put("mCategoryDTOList",mCategoryDTOList);
		}
		return SUCCESS;
	}
	public List<ProductInfoDTO> getProductInfoDTOList(){
		return productInfoDTOList;
	}
	public void setProductInfoDTOList(List<ProductInfoDTO> productInfoDTOList){
		this.productInfoDTOList=productInfoDTOList;
	}
	public Map<String,Object> getSession(){
		return session;
	}
	public void setSession(Map<String,Object> session){
		this.session=session;
	}

}
