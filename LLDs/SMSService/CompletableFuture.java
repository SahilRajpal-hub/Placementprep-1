/*
🧠 Summary Table

#Category         	 #Common Methods
Async Start	         runAsync(), supplyAsync()
Transform / Chain	 thenApply(), thenCompose(), thenAccept()
Side Effects	     thenRun(), whenComplete()
Error Handling	     handle(), handleAsync(), exceptionally()
Combine Futures	     thenCombine(), allOf(), anyOf()
 




*/
//example

CompletableFuture<String> userIdFuture = CompletableFuture
    // Step 1: Start Async Task
    .supplyAsync(() -> fetchUserId("sahil@email.com"), executor)

    // Step 2: Chain another async call (dependent)
    .thenCompose(userId -> fetchUserProfile(userId))

    // Step 3: Transform the profile
    .thenApply(profile -> profile.toUpperCase())

    // Step 4: Error handling
    .exceptionally(ex -> {
        System.out.println("Error occurred: " + ex.getMessage());
        return "Default User Profile";
    });

// Step 5: Combine with another future
CompletableFuture<String> greetingFuture = CompletableFuture.supplyAsync(() -> fetchGreeting(), executor);

CompletableFuture<Void> combined = userIdFuture.thenAcceptBoth(greetingFuture, (profile, greeting) -> {
    System.out.println(greeting + ", " + profile);
});

combined.join();