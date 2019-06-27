import requests
import jsonify, json
from tabulate import tabulate

headers = {
        'Content-Type': 'application/json',
    }

def cli_to_uri(objs, ip='127.0.0.1', port=8080):
    url='https://'+ip+ ":"+str(port)+"/"+"/".join(objs)
    return url

def dict_to_tab(d):
    pass
    dictlist=[]
    temp=[]
    for key, value in d.iteritems():
      dictlist.append(value)
    for i in dictlist:
      for j in i:
        temp.append(j)
    return tabulate(temp, headers="keys", tablefmt="grid")

def dict_to_one(data):
    pass
    dictlist=[]
    for key, value in data.iteritems():
      dictlist.append(value)
    return tabulate(dictlist, headers="keys", tablefmt="grid")





def cluster_setdate(date = None,override_ntp = None):
    payload = {
        "date": date,
        "override-ntp": override_ntp

    }
    if override_ntp and date != None:
        uri = cli_to_uri(['cluster', 'date'])
        req_put = requests.post(uri, headers=headers, data=json.dumps(payload))
        if req_put.status_code == 200:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))
        elif req_put.status_code == 401:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))

    else:
        uri = cli_to_uri(['cluster', 'date'])
        req_put = requests.get(uri)
        if req_put.status_code == 200:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))
        elif req_put.status_code == 401:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))

def cluster_mode(s=None, status=None, mode =None, db_only=None):
    if s != None:
        uri = cli_to_uri(['cluster'])
        req_put = requests.get(uri)
        if req_put.status_code == 200:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))
        elif req_put.status_code == 401:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))
    elif status != None:
        uri = cli_to_uri(['cluster'])
        req_put = requests.get(uri)
        if req_put.status_code == 200:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))
        elif req_put.status_code == 401:
            response = req_put.json()
            data = sorted([(k, v) for k, v in response.items()])
            return (tabulate(data, tablefmt="grid"))
    elif mode and db_only != None:
        payload = {
            "mode": mode,
            "db_only": db_only

        }
        uri = cli_to_uri(['cluster'])
        req_put = requests.post(uri, headers=headers, data=json.dumps(payload))
        if req_put.status_code == 200:
           response = req_put.json()
           data = sorted([(k, v) for k, v in response.items()])
           return (tabulate(data, tablefmt="grid"))
        elif req_put.status_code == 401:
             response = req_put.json()
             data = sorted([(k, v) for k, v in response.items()])
             return (tabulate(data, tablefmt="grid"))


#http://
def main():
        username = raw_input("What is your name? ")
	# verb 
	# payload
	url = cli_to_uri('127.0.0.1', 8080, ['users', 'admins', username])
        print url
	#request.post(url,payload)
        
        dict_to_tab(dict)



if __name__ == '__main__':
	main()
