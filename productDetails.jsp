<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./css/blue.css">
<title>商品詳細</title>
</head>
<body>
	<jsp:include page="header.jsp" />
	<div id="contents">
		<h1>商品詳細</h1>
		<s:if test="productInfoDTO!=null">
			<div class="main-container">
				<div class="box1-details">
					<img src='<s:property value="productInfoDTO.imageFilePath"/>/<s:property value="productInfoDTO.imageFileName"/>'
						class="item-image-details" /><br>
				</div>
				<div class="box2-details">
					<s:form action="AddCartAction">
						<table class="list-table details">
							<colgroup>
								<col width="45%">
								<col width="55%">
							</colgroup>
							<tr>
								<th scope="row"><s:label value="商品名" /></th>
								<td><s:property value="productInfoDTO.productName" /></td>
							</tr>
							<tr>
								<th scope="row"><s:label value="商品名ふりがな" /></th>
								<td><s:property value="productInfoDTO.productNameKana" /></td>
							</tr>
							<tr>
								<th scope="row"><s:label value="値段" /></th>
								<td><s:property value="productInfoDTO.price" />円</td>
							</tr>
							<tr>
								<th scope="row"><s:label value="購入個数" /></th>
								<td><s:select name="productCount"
										list="%{productCountList}" />個</td>
							</tr>
							<tr>
								<th scope="row"><s:label value="発売会社名" /></th>
								<td><s:property value="productInfoDTO.releaseCompany" /></td>
							</tr>
							<tr>
								<th scope="row"><s:label value="発売年月日" /></th>
								<td><s:property value="productInfoDTO.releaseDate" /></td>
							</tr>
							<tr>
								<th scope="row"><s:label value="商品詳細情報" /></th>
								<td><s:property value="productInfoDTO.productDescription" /></td>
							</tr>
						</table>
						<s:hidden name="productId" value="%{productInfoDTO.productId}" />
						<div class="tC mt20">
							<s:submit value="カートに追加" class="submit_btn" />
						</div>
					</s:form>
				</div>
			</div>
			<!-- 関連商品 -->
			<div class="under-details">
				<s:if test="relatedProductList!=null && relatedProductList.size()>0">
				<div class="kanren">関連商品</div>
					<ul>
						<s:iterator value="relatedProductList">
							<li><a href='<s:url action="ProductDetailsAction">
									<s:param name="productId" value="%{productId}"/></s:url>'>
									<img src='<s:property value="imageFilePath"/>/<s:property value="imageFileName"/>'
									class="item-image-details" /> <s:property value="productName" />
								</a>
							</li>
						</s:iterator>
					</ul>
				</s:if>
			</div>
		</s:if>
		<s:else>
			<div class="info blueText">商品の詳細情報がありません。</div>
		</s:else>
	</div>
</body>
</html>