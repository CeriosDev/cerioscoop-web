<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

	<link href='/cerioscoop-web/css/masterdetail.css' type='text/css' rel='stylesheet' />
	<link href='/cerioscoop-web/css/shared.css' type='text/css' rel='stylesheet' />
	
</head>
<body>
 <div><jsp:include page="/jsp/shared/navbar.jsp"></jsp:include></div>	
 		<div class="home-employee">
		<div id="navbar-employee">
			<jsp:include page="/jsp/employee/navbar-employee.jsp"></jsp:include>
		</div>
		<div class="home-content-employee"></div>
	</div>
 <div>
<h1>Delete movie</h1>
<form method="POST" action="/cerioscoop-web/DeleteMovieServlet"> 
<br>Select Movie:<br>
 <input type="text" name="movie_id" required><br>
<br><input type="submit" name="submitit" value="Submit">
</form> 

<table>
<thead><th>MovieId</th><th>Category</th><th>Title</th><th>Duration</th><th>Type</th><th>Language</th><th>Description</th><th>Trailer</th></thead>
<tbody>

<c:forEach items="${currentMovies}" var="currentMovies">
  
<tr>
	<td>${currentMovies.movieId}</td>
	<td>${currentMovies.category}</td>
	<td>${currentMovies.title}</td>
	<td>${currentMovies.minutes}</td>
	<td>${currentMovies.movieType}</td>
	<td>${currentMovies.language}</td>
	<td>${currentMovies.description}</td>
	<td>${currentMovies.trailer}</td>
	</tr>

</c:forEach>

</tbody>
</table>
</div>
	<jsp:include page="/jsp/shared/footer.jsp" />
</body>
</html>