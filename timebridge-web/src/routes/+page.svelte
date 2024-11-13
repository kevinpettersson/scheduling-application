<script> 
    let messege = "Webbapp för OOP"   
</script>    

<style>
    body {
        font-family: Arial, Sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f9f9f9;
        color: black;
    }    
    header {
        background-color: #6200ea;
        color: white;
        padding: 1rem;
        text-align: center;
    }

    main {
        padding: 2rem;
        text-align: center;
    }

    footer {
        background-color: #333;
        color: white;
        padding: 1rem;
        position: fixed;
        bottom: 0;
        width: 100%;
        text-align: center;
    }
</style>

<header>
    <h1>
        Scheduling app website!
    </h1>
</header>

<main>  
    <h2>
        {messege}
    </h2>
    <p>
        text hahahahah text hahahahah
    </p>
    <!-- INTERACTIVE COMPONENTS HERE -->
     <button on:click={() => alert('ButtonHasBeen clicked but does it do anythin?!')}>CLICK ME!!!!!</button>
    
</main>

<footer>
    <p>
        &copy; 2024 Object Oriented Project Course
    </p>
    <p>
        &copy; group 12
    </p>
    <p>
        Kevin, Natalie, Nils, Oscar, Sebastian
    </p>
</footer>


