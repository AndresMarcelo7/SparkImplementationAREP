let btn = document.getElementById('buttonB');

btn.addEventListener('click',() =>{
    let numbers = "kk";
    axios.post('https://marcelo-spark-arep.herokuapp.com/testPost', numbers)
        .then(res => {
            console.log("succesfull");
        })
        .catch( error =>{
            console.log(error);
        })
    axios.post('http://localhost:36000/testPost', numbers)
        .then(res => {
            doPost(res);
        })
        .catch( error =>{
            console.log(error);
        })
});
