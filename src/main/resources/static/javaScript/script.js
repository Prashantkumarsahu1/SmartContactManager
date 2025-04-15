console.log("Script loaded");

let currentTheme = getTheme();

changeTheme();

//function to change the theme
function changeTheme(){
    // Apply the current theme to the <html> element
    document.querySelector('html').classList.add(currentTheme);

    //set the listner to change theme button
    // Find the theme change button
    const changeThemeButton = document.querySelector('#theme_change_button');
    
    
    //set the listner to change theme button
    changeThemeButton.addEventListener("click",(event)=>{
        console.log("change theme button clicked");
        const oldTheme = currentTheme;
        if(currentTheme === "dark"){
            // change the theme colour to light
           currentTheme = "light";
        }else{
            // change the theme colour to dark
            currentTheme = "dark";
        }
        setTheme(currentTheme);
        document.querySelector('html').classList.remove(oldTheme);
        document.querySelector('html').classList.add(currentTheme);
        changeThemeButton.querySelector("span").textContent= currentTheme=="light"?"Dark":"Light";
        

    
        
    });
    

}

//Set the theme on local storage
function setTheme(theme){
    localStorage.setItem("theme", theme);
}

//Get the theme from local storage
function getTheme(){
    let theme = localStorage.getItem("theme");
    return theme ? theme : "light";
}