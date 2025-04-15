console.log("admin.js loaded");
// for the image preview
document.querySelector("#image_file_input").addEventListener("change", function(event) {

    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = function() {
        document.querySelector("#upload_image_preview").setAttribute("src", reader.result);
    };
    reader.readAsDataURL(file);

});