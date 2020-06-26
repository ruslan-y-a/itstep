<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>New User</title>
	</head>
	<body>
		<h1 style="color: green">New User</h1>
		<form action="save.html" method="get">			
			<br>
			<label for="name">Name</label>
			<br>
			<input type="text" id="name" name="name">
			<br>
			<br>
			<label for="password">Password</label>
			<br>
			<input type="password" id="password" name="password">
			<br>
			<br>
			<label for="email">Email</label>
			<br>
			<input type="email" id="email" name="email">
			<br>
			<br>			
			<label for="roleid">Role:/label>
			<br>
			<select id="roleid" name="roleid">				
				<option value="0">ADMIN</option>
				<option value="1">CLIENT</option>
				<option value="2">PRODUCT MANAGER</option>
				<option value="3">MANAGER</option>
				<option value="4">CASHIER</option>
			</select>
			<br>
			<button type="submit">Save</button>
		</form>
	</body>
</html>
