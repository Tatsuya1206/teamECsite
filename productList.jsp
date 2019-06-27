<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/blue.css">
<title>商品一覧画面</title>
</head>
<body>

<jsp:include page="header.jsp"/>
<div id="contents">
<h1>商品一覧画面</h1>
<s:if test="keywordsErrorMessageList!=null && keywordsErrorMessageList.size()>0">
	<div class="info redText">
		<s:iterator value="keywordsErrorMessageList"><s:property /><br></s:iterator>
	</div>
</s:if>
<s:elseif test="productInfoDTOList!=null && productInfoDTOList.size()>0">
	<div class="product-list-box">
		<ul>
		<s:iterator value="productInfoDTOList">
			<li>
			<a href='<s:url action="ProductDetailsAction">
			<s:param name="productId" value="%{productId}"/>
			</s:url>'><span><img src='<s:property value="imageFilePath"/>/<s:property value="imageFileName"/>' class="item_image_box_200"/></span><br>
			<s:property value="productName"/><br>
			<s:property value="productNameKana"/><br>
			<s:property value="price"/>円<br>
			</a>
			</li>
		</s:iterator>
		</ul>
	</div>
</s:elseif>
<s:else>
	<div class="info blueText">検索結果がありません。</div>
</s:else>

</div>
</body>
</html>