url = "http://localhost:8080/getData/";
name;
function init(){
  generateBoard();
  getName("http://localhost:8080/getName/");
  getJson(url)
  pollServerLoop = window.setInterval(getJson(url), 3000);
}
function disconnect(){
  window.clearInterval(pollServerLoop);
}

function generateBoard(){
  gameBoard = document.getElementById('game-board')
  gameBoardData = "";
  for(i = 0; i < 8; i++){
    for (var j = 0; j < 4; j++) {
      gameBoardData += '<div class="boardSquare col'+i%2+'" id="square'+(j*2)+i+'">&nbsp;</div><div class="boardSquare col'+(i+1)%2+'" id="square'+((j*2)+1)+i+'">&nbsp;</div>';
      }
      gameBoardData += '<br>';
  }
  gameBoard.innerHTML = gameBoardData;
}

function jsonToBoard(json){
  for(i = 0; i < 8; i++){
    for (var j = 0; j < 8; j++) {
      placeItem(json.board[i][j],i,j);
    }
  }
}

function placeItem(item,x,y){
  //items: _0 clear items
  //       _1 white counter
  //       _2 white king
  //       _3 black counter
  //       _4 black king
  //       0_ unhighlighted
  //       1_ possible move indicator
  if(item>=10){
    highlighted = '<img class="entityIcon" style="cursor:pointer;" onclick="clickedSquare('+x+','+y+')" src="./highlight.png">';
  }else{
    highlighted = '';
  }
  switch (item%10) {
    case 0:
      piece = "";
      break;
    case 1:
      piece = '<img class="entityIcon" src="./whiteCounter.png">'
      break;
    case 2:
      piece = '<img class="entityIcon" src="./whiteCrown.png">'
      break;
    case 3:
      piece = '<img class="entityIcon" src="./blackCounter.png">'
      break;
    case 4:
      piece = '<img class="entityIcon" src="./blackCrown.png">'
      break;
    default:
      piece = "";//shouldnt occur, 5 or above
  }
  //document.getElementById('square'+x+y).innerHTML="highlighted:"+highlighted+" piece:"+piece;
  document.getElementById('square'+x+y).innerHTML = "&nbsp;"+piece+highlighted+"&nbsp;";
  //console.log('highlighted:'+highlighted+' piece:'+piece+' square'+x+y);
}
function getJson(url){
  //Makes request to server for json and then parses json into object, returning that
  const req = new XMLHttpRequest();
  req.onreadystatechange = function() {
      if (req.status == 404) {
          console.log("404");
          return false;
      }

      if (!(req.readyState == 4 && req.status == 200))
          return false;

      /*const json = (function(raw) {
        console.log(raw);
          try {
              return JSON.parse(raw);
          } catch (err) {
              return false;
          }
      })(req.response);*/
      json = req.response;
      console.log(json);
      if (!json)
          return false;
      else {
        jsonToBoard(json);
      }

  };
  req.open("GET", url, true);
  req.responseType = 'json';
  req.send();

}

function getName(url){
  //Makes request to server for json and then parses json into object, returning that
  const req = new XMLHttpRequest();
  req.onreadystatechange = function() {
      /*const json = (function(raw) {
        console.log(raw);
          try {
              return JSON.parse(raw);
          } catch (err) {
              return false;
          }
      })(req.response);*/
      name = req.response;
	  console.log('name:' +name)

  };
  req.open("GET", url, true);
  req.responseType = 'json';
  req.send();

}

function clickedSquare(x,y){
  sendMove("http://localhost:8080/sendData/", x, y)
  
}

function sendMove(url, x , y){
  //Makes request to server for json and then parses json into object, returning that
  const req = new XMLHttpRequest();
  req.onreadystatechange = function() {
		if (req.status == 404) {
          console.log("404");
          return false;
      }

      if (!(req.readyState == 4 && req.status == 200))
          return false;
      else {
		req.send(name+','+x+','+y)
      }

  };
  req.open("PUT", url, true);
  console.log(name+','+x+','+y);
  req.send();
}
