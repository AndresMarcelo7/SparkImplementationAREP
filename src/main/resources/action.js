let btn = document.getElementById('buttonB');
btn.addEventListener('click',() =>{
    let numbers = "kk";
	var deleteFoodConfig = { headers: { 'Content-Type': 'text/html'} };  
    var reqFoodId = JSON.stringify({"123":"amigo"});
axios.post('https://marcelo-spark-arep.herokuapp.com/testPost', reqFoodId,deleteFoodConfig)
        .then(res => {
            console.log("succesfull");
        })
        .catch( error =>{
            console.log(error);
        })
    axios.post('http://localhost:36000/testPost', reqFoodId,deleteFoodConfig)
        .then(res => {
            console.log("succesfull");
        })
        .catch( error =>{
            console.log(error);
        })
});
