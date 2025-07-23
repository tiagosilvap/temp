from sqlalchemy import create_engine, MetaData, inspect
import pymysql

# Configurações
host = "hotpay-db-read.vulcano.rocks"
user = "tiago.pereira_read_NRGHu"
password = "g4aXNn5Q-GWJ430lPQHy"
database = "hotpay"
output_file = "output.puml"

# Conexão
engine = create_engine(f"mysql+pymysql://{user}:{password}@{host}/{database}")
metadata = MetaData()
metadata.reflect(bind=engine)
inspector = inspect(engine)

def is_fk(column_name, table_name):
    fks = inspector.get_foreign_keys(table_name)
    return any(column_name == fk['constrained_columns'][0] for fk in fks)

def get_relationships():
    relationships = []
    for table in inspector.get_table_names():
        fks = inspector.get_foreign_keys(table)
        for fk in fks:
            source = table
            target = fk['referred_table']
            relationships.append((target, source))
    return relationships

with open(output_file, "w") as f:
    f.write("@startuml\n")
    f.write("' Use o modo E-R\n")
    f.write("!define table(x) class x << (T,#FFAAAA) >>\n")
    f.write("!define primary_key(x) <u>x</u>\n\n")
    f.write("' Tabelas existentes\n\n")

    for table_name, table in metadata.tables.items():
        f.write(f"table({table_name}) {{\n")
        pk_column = list(table.primary_key.columns)[0].name if table.primary_key.columns else None

        for column in table.columns:
            col_name = column.name
            is_primary = (col_name == pk_column)
            fk_tag = " (FK)" if is_fk(col_name, table_name) else ""

            if is_primary:
                f.write(f"  primary_key({col_name}){fk_tag}\n")
            else:
                f.write(f"  {col_name}{fk_tag}\n")
        f.write("}\n\n")

    f.write("' Relacionamentos\n\n")
    for (parent, child) in get_relationships():
        f.write(f"{parent} ||--o{{ {child}\n")

    f.write("@enduml\n")

print(f"Arquivo '{output_file}' gerado com sucesso.")
