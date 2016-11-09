var babelCss = document.createElement("link");
babelCss.href = "babel.css";
babelCss.rel= "stylesheet";
document.head.appendChild(babelCss);

var navigation = document.createElement("nav");
navigation.role = "navigation";
navigation.id = "header";
navigation.class = "navbar navbar-default navbar-fixed-top";
document.body.appendChild(navigation);

var container = $("<div class='container'></div>" );
container.appendTo("#header");

var navigationDiv = $("<div id='navigation' class='collapse navbar-collapse'></div>");
navigationDiv.appendTo(container);

var ul = $("<ul class='nav navbar-nav'></ul>");
ul.appendTo(navigationDiv);

var li1 = $("<li><a id='tournamentsLink' href='tournament/getAll' title='Go see tournaments'>Tournaments</a></li>");
li1.appendTo(ul);

/*
	<nav role="navigation" id="header" class="navbar navbar-default navbar-fixed-top">
		<div class="container">		
			<div id="navigation" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a id="tournamentsLink" href="tournament/getAll" title="Go see tournaments">Tournaments</a></li>
					<li><a id="teamsLink" href="team/getAll" title="Go see teams">Teams</a></li>
					<li><a id="tournamentTeamsLink" href="tournamentTeam/getAll" title="Go see tournament teams">Tourn. teams</a></li>
					<li><a id="playersLink" href="player/getAll" title="Go see players">Players</a></li>
					<li><a id="tournamentPlayersLink" href="tournamentPlayer/getAll" title="Go see tournament players">Tourn. players</a></li>
					<li><a id="gamesLink" href="scorer/getAll" title="Go see games">Games</a></li>
					<li><a id="scorersLink" href="scorer/getAll" title="Go see scorers">Scorers</a></li>
					<li><a id="userRolesLink" href="userRole/getAll" title="Go see user roles">User roles</a></li>
					<li><a id="usersLink" href="user/getAll" title="Go see users">Users</a></li>
					<li><a id="statusesLink" href="scorer/getAll" title="Go see statuses">Statuses</a></li>
					<li><a id="betsLink" href="scorer/getAll" title="Go see bets">Bets</a></li>
					<li><a id="betResultsLink" href="scorer/getAll" title="Go see bet results">Bet results</a></li>
				</ul>
			</div>
		</div>
	</nav>
	*/