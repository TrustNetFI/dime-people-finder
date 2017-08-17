<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="xdi2.core.ContextNode" %>
<%@ page import="xdi2.core.LiteralNode" %>
<%@ page import="xdi2.core.util.XDIAddressUtil" %>
<%@ page import="xdi2.core.syntax.XDIAddress" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>

<title>HIIT DiMe People Finder</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta charset="UTF-8"> 

<link rel="stylesheet" type="text/css" href="style.css"> 

<script type="text/javascript" src="/jquery-2.0.3.min.js"></script>

<script type="text/javascript">

$(document).ready(function() {

	populate();
});

var divtags = {};

function populate() {

	var target = "/people";

	$.ajax({url:target, type:'get'})
	.done(function(data) {
		$('#plist').empty();
		for (i in data) {
			var address = data[i].address;
			var img = $("<img src=\"/images/logo-dime.jpg\" style=\"float:right\" height=\"60\">");
			var btn = $("<div style=\"float:right;\"><input style=\"padding:10px\" type=\"button\" value=\"Connect\"></div>");
			var tags = data[i].tags;
			var div = $("<div style=\"border:2px solid black; margin:10px; padding:10px;\"></div>");
			var p = $("<p><b>Address:</b> " + address + "</p>");
			var p2 = $("<p></p>");
			p2.attr("style","clear:both;");
			div.append(img);
			div.append(btn);
			div.append(p);
			for (ii in tags) {
				var tag = tags[ii];
				var span = $("<span>Tag: " + tag + "</span>");
				span.attr("style","background-color:lightgray;padding:10px;margin-right:30px;");
				p2.append(span);
			}
			div.append(p2);
			$('#plist').append(div);
			div.data("tags", tags);
			div.hide();
		}
	})
	.fail(function(err) {
		alert("error: " + JSON.stringify(err));
	});
}

function search() {

	var searchtag = $('#searchtag').val();

	$('#plist').children("div").each(function () {
		if ($(this).data("tags").indexOf(searchtag) != -1) $(this).show(); else $(this).hide();
	});
}

function show() {

	$('#plist').children("div").each(function () {
		$(this).show();
	});
}

function hide() {

	$('#plist').children("div").each(function () {
		$(this).hide();
	});
}

</script>

</head>

<body style="padding:20px">

<img src="/images/logo-dime.jpg" style="float:left" height="60">

<h1>People Finder</h1>

<p style="clear:both;margin-top:20px;">Search by tag: <input type="text" id="searchtag" name="searchtag" size="10" value="sovrin"></p>
<p><button onclick="search();">Search</button><button onclick="show();">Show all</button><button onclick="hide();">Hide all</button></p>

<div id="plist"></div>

</body>

</html>
