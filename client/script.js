function init(){
generateBoard();

}
function startPollLoop(){

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
function placeItem(item,x,y){
  //items: _0 clear items
  //       _1 white counter
  //       _2 white king
  //       _3 black counter
  //       _4 black king
  //       0_ unhighlighted
  //       1_ possible move indicator
  if(item>=10){
    highlighted = '<img class="entityIcon" src="./highlight.png">';
  }else{
    highlighted = false;
  }
  switch (item%10) {
    case 0:
      piece = "";
      break;
    case 1:
      piece = '<img class="entityIcon" src="./highlight.png">'
      break;
    case 2:
      piece = '<img class="entityIcon" src="./highlight.png">'
      break;
    case 3:
      piece = '<img class="entityIcon" src="./blackCounter.png">'
      break;
    case 4:
      piece = '<img class="entityIcon" src="./blackCounter.png">'
      break;
    default:
      piece = "void";
  }
  /*document.getElementById('square'+x+y).innerHTML="highlighted:"+highlighted+" piece:"+piece;*/
  document.getElementById('square'+x+y).innerHTML = "&nbsp;"+highlighted+piece+"&nbsp;";

  console.log('highlighted:'+highlighted+' piece:'+piece+' square'+x+y);
}
//function ()
