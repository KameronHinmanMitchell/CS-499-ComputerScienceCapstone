from pymongo import MongoClient 
from bson.objectid import ObjectId 

class CRUD(object): 
    """ CRUD operations for Animal collection in MongoDB """ 

    def __init__(self, USER, PASS): 
        # Initializing the MongoClient. This helps to access the MongoDB 
        # databases and collections. This is hard-wired to use the aac 
        # database, the animals collection, and the aac user. 
        # 
        # You must edit the password below for your environment. 
        # 
        # Connection Variables 
        # 
        #USER = 'aacuser' 
        #PASS = 'Pass123' 
        HOST = 'localhost' 
        PORT = 27017 
        DB = 'aac' 
        COL = 'animals' 
        # 
        # Initialize Connection 
        # 
        self.client = MongoClient('mongodb://%s:%s@%s:%d' % (USER, PASS, HOST, PORT))
        self.database = self.client['%s' % (DB)] 
        self.collection = self.database['%s' % (COL)]
        
        print("Successful connection")

    # Create a method to return the next available record number for use in the create method
            
    # Complete this create method to implement the C in CRUD. 
     #Method to implement the C in CRUD.
    def create(self, data):
       
        if data is not None:
            inserted = self.database.animals.insert_one(data)  # data should be dictionary
    
            if inserted != 0:
                return True
            return False
        else:
            raise Exception("Nothing to save, data parameter is empty")
 
    #Method to implement the R in CRUD.
    def read(self, read_data):  
       
        if read_data is not None:
            read = self.database.animals.find(read_data, {"_id": False})
        else:
            raise Exception("Nothing to read, parameter is empty.")
        return read

    #Method to implement the U in CRUD.
    def update(self, read_data, Update_data):  

        if read_data and Update_data is not None:
            updated = self.database.animals.update_many(read_data, {"$set": Update_data})
        else:
            raise Exception("Nothing to update, parameters are empty.")
        return updated.modified_count

    #Method to implement the D in CRUD.
    def delete(self, Delete_data):  
        
        if Delete_data is not None:
            deleted = self.database.animals.delete_many(Delete_data)
        else:
            raise Exception("nothing to delete, parameters are empty.")
        return deleted.deleted_count