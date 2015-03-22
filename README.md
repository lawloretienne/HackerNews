# HackerNews
Pulls top stories from HackerNews API (https://github.com/HackerNews/API)

The current implementation does not display more than 5 stories for the HackerNews API and the stories are showing out of order.

Given more time, I would have used Reactive Extension to solve this problem (https://github.com/ReactiveX/RxJava). 

I would use an Observable to process all of the events (requests for different top stories) and merge the results so then I could add the top stories to the adapter in the correct order.
