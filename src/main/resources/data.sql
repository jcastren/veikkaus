INSERT INTO status (status_number, description)
VALUES (1, 'INITIAL');

INSERT INTO user_role (name)
VALUES ('Admin');

INSERT INTO user (email, name, password, user_role_id)
VALUES ('joonas_castren@yahoo.com', 'Joonas', '123', 1);

INSERT INTO tournament (name, year)
VALUES ('EURO2016', 2016);
INSERT INTO tournament (name, year)
VALUES ('EURO2021', 2021);

INSERT INTO team (name)
VALUES ('France');
INSERT INTO team (name)
VALUES ('Albania');
INSERT INTO team (name)
VALUES ('Austria');
INSERT INTO team (name)
VALUES ('Belgium');
INSERT INTO team (name)
VALUES ('Croatia');
INSERT INTO team (name)
VALUES ('Czech Republic');
INSERT INTO team (name)
VALUES ('England');
INSERT INTO team (name)
VALUES ('Germany');
INSERT INTO team (name)
VALUES ('Hungary');
INSERT INTO team (name)
VALUES ('Iceland');
INSERT INTO team (name)
VALUES ('Republic of Ireland');
INSERT INTO team (name)
VALUES ('Italy');
INSERT INTO team (name)
VALUES ('Northern Ireland');
INSERT INTO team (name)
VALUES ('Netherlands');
INSERT INTO team (name)
VALUES ('Poland');
INSERT INTO team (name)
VALUES ('Portugal');
INSERT INTO team (name)
VALUES ('Romania');
INSERT INTO team (name)
VALUES ('Russia');
INSERT INTO team (name)
VALUES ('Slovakia');
INSERT INTO team (name)
VALUES ('Spain');
INSERT INTO team (name)
VALUES ('Sweden');
INSERT INTO team (name)
VALUES ('Switzerland');
INSERT INTO team (name)
VALUES ('Turkey');
INSERT INTO team (name)
VALUES ('Ukraine');
INSERT INTO team (name)
VALUES ('Wales');

INSERT INTO tournament_team (team_id, tournament_id)
VALUES (1, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (1, 2);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (2, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (3, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (4, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (5, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (6, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (7, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (8, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (9, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (10, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (11, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (12, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (13, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (14, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (15, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (16, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (17, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (18, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (19, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (20, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (21, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (22, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (23, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (24, 1);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (14, 2);
INSERT INTO tournament_team (team_id, tournament_id)
VALUES (1, 2);

INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 1, 16, '2016-06-10', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 2, 21, '2016-06-11', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 24, 18, '2016-06-11', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 7, 17, '2016-06-11', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 14, 13, '2016-06-12', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 8, 23, '2016-06-12', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 22, 5, '2016-06-12', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 19, 6, '2016-06-13', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 11, 20, '2016-06-13', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 4, 12, '2016-06-13', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 3, 9, '2016-06-14', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 15, 10, '2016-06-14', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 16, 21, '2016-06-15', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 1, 2, '2016-06-15', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 17, 18, '2016-06-15', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 7, 24, '2016-06-16', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 23, 13, '2016-06-16', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 8, 14, '2016-06-16', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 6, 5, '2016-06-17', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 19, 22, '2016-06-17', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 12, 20, '2016-06-17', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 4, 11, '2016-06-18', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 10, 9, '2016-06-18', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 14, 3, '2016-06-18', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 21, 1, '2016-06-19', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 16, 2, '2016-06-19', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 18, 7, '2016-06-20', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 17, 24, '2016-06-20', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 13, 8, '2016-06-21', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 23, 14, '2016-06-21', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 5, 19, '2016-06-21', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 6, 22, '2016-06-21', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 20, 4, '2016-06-22', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 12, 11, '2016-06-22', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 10, 3, '2016-06-22', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (1, 9, 15, '2016-06-22', -999, -999);
INSERT INTO game (tournament_id, home_team_id, away_team_id, game_date, home_score, away_score)
VALUES (2, 9, 15, '2021-06-15', -999, -999);

INSERT INTO player (first_name, last_name)
VALUES ('Ruud', 'Gullit');
INSERT INTO player (first_name, last_name)
VALUES ('Marco', 'van Basten');
INSERT INTO player (first_name, last_name)
VALUES ('Zidedine', 'Zidane');
INSERT INTO player (first_name, last_name)
VALUES ('Karim', 'Benzema');

INSERT INTO tournament_player (goals, player_id, tournament_team_id)
VALUES (2, 1, 26);
INSERT INTO tournament_player (goals, player_id, tournament_team_id)
VALUES (3, 2, 26);
INSERT INTO tournament_player (goals, player_id, tournament_team_id)
VALUES (6, 4, 1);
INSERT INTO tournament_player (goals, player_id, tournament_team_id)
VALUES (4, 3, 27);
INSERT INTO tournament_player (goals, player_id, tournament_team_id)
VALUES (5, 4, 27);
