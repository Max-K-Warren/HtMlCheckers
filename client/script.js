function init(){
generateBoard();

}
function startPollLoop(){

}

function generateBoard(){
  gameBoard = document.getElementById('game-board')
  gameBoardData = "";
  for(i = 0; i < 8; i++){
  console.log(i+1%2)
    for (var j = 0; j < 4; j++) {
      gameBoardData += '<div class="boardSquare col'+i%2+'">a</div><div class="boardSquare col'+i%2+'"></div>';
      console.log('<div class="boardSquare col'+i%2+'">a</div><div class="boardSquare col'+(i%2+1)+'"></div>')
      }
      gameBoardData += '<br>';
  }
  gameBoard.innerHTML = gameBoardData;
}
