$("<link href='/veikkaus/babel.css' rel='stylesheet'>").appendTo("head");

$("<nav role='navigation' id='header' class='navbar navbar-default navbar-fixed-top'>").appendTo("body");
$("<div id='container' class='container'>").appendTo("#header");
$("<div id='navigation' class='collapse navbar-collapse'>").appendTo("#container");
$("<ul id='navigationList' class='nav navbar-nav'>").appendTo("#navigation");
$("<li><a class='showTooltip' href='/veikkaus/tournament/getAll' title='Go see tournaments'>Tournaments</a>").appendTo("#navigationList");
$("<li><a class='showTooltip' href='/veikkaus/team/getAll' title='Go see teams'>Teams</a>").appendTo("#navigationList");
$("<li><a class='showTooltip' href='/veikkaus/tournamentTeam/getAll' title='Go see tournament teams'>Tourn. teams</a>").appendTo("#navigationList");
$("<li><a class='showTooltip' href='/veikkaus/player/getAll' title='Go see players'>Players</a>").appendTo("#navigationList");
$("<li><a class='showTooltip' href='/veikkaus/tournamentPlayer/getAll' title='Go see tournament players'>Tourn. players</a>").appendTo("#navigationList");
$("<li><a class='showTooltip' href='/veikkaus/game/getAll' title='Go see games'>Games</a>").appendTo("#navigationList");
$("<li><a class='showTooltip' href='/veikkaus/scorer/getAll' title='Go see scorers'>Scorers</a>").appendTo("#navigationList");
$("<li><a class='showTooltip' href='/veikkaus/userRole/getAll' title='Go see user roles'>User roles</a>").appendTo("#navigationList");
$("<li><a class='showTooltip' href='/veikkaus/user/getAll' title='Go see users'>Users</a>").appendTo("#navigationList");
$("<li><a class='showTooltip' href='/veikkaus/status/getAll' title='Go see statuses'>Statuses</a>").appendTo("#navigationList");
$("<li><a class='showTooltip' href='/veikkaus/bet/getAll' title='Go see bets'>Bets</a>").appendTo("#navigationList");
$("<li><a class='showTooltip' href='/veikkaus/betResult/getAll' title='Go see bet results'>Bet results</a>").appendTo("#navigationList");

// Following code snippet loops all items whose id ends with Link and adds a JQuery tooltip to them
/*$("[id$=Link]").each(function() {
		$(this).tooltip();
});*/

// However, it's wiser to use classes for this functionality:
$(".show-tooltip").each(function() {
		$(this).tooltip();
});

// Adding to href of every anchor object string '/veikkaus' to beginning to get the links work
// both in static pages and Thymeleaf templates
/*
$("a").attr("href", function() {
	console.log("this.href: " + this.href);
	return "/veikkaus/" + this.href;
	this.href.
});
*/