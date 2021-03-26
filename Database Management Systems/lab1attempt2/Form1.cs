using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;

namespace lab1attempt2
{
    public partial class Form1 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=DESKTOP-E7JFHHC\\SQLEXPRESS;Initial Catalog=sem2MP;Integrated Security=True");
        SqlDataAdapter da = new SqlDataAdapter();
        DataSet ds = new DataSet();
        DataSet ds1 = new DataSet();
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            da.SelectCommand = new SqlCommand("SELECT * FROM Album", cs);
            ds.Clear();
            da.Fill(ds);
            dataGridView1.DataSource = ds.Tables[0];

            textBox1.Text = "";
            textBox2.Text = "";
            textBox3.Text = "";
            textBox4.Text = "";
        }

        private void button2_Click(object sender, EventArgs e)
        {
            da.SelectCommand = new SqlCommand("SELECT * FROM Song", cs);
            ds1.Clear();
            da.Fill(ds1);
            dataGridView2.DataSource = ds1.Tables[0];

            textBox1.Text = "";
            textBox2.Text = "";
            textBox3.Text = "";
            textBox4.Text = "";
        }

        //FILTER
        private void button6_Click(object sender, EventArgs e)
        {
            try
            {
                da.SelectCommand = new SqlCommand("SELECT * FROM Song WHERE alid=@s", cs);
                da.SelectCommand.Parameters.Add("@s", SqlDbType.Int).Value = Int32.Parse(textBox4.Text);
                ds1.Clear();
                da.Fill(ds1);
                dataGridView2.DataSource = ds1.Tables[0];
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
        }

        //INSERT
        private void button3_Click(object sender, EventArgs e)
        {
            try
            {
                da.InsertCommand = new SqlCommand("INSERT INTO Song(title, length, alid) VALUES(@t,@l,@a)", cs);
                //da.InsertCommand.Parameters.Add("@s", SqlDbType.Int).Value = Int32.Parse(textBox1.Text);
                da.InsertCommand.Parameters.Add("@t", SqlDbType.NVarChar).Value = textBox2.Text;
                da.InsertCommand.Parameters.Add("@l", SqlDbType.Real).Value =
               float.Parse(textBox3.Text);
                da.InsertCommand.Parameters.Add("@a", SqlDbType.Int).Value =
               Int32.Parse(textBox4.Text);

                cs.Open();
                da.InsertCommand.ExecuteNonQuery();
                MessageBox.Show("Inserted Succesfull to the Database");
                cs.Close();
                // already inserted - apear in the list

                da.SelectCommand = new SqlCommand("SELECT * FROM Song", cs);
                ds1.Clear();
                da.Fill(ds1);
                dataGridView2.DataSource = ds1.Tables[0];
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
        }

        //REMOVE
        private void button4_Click(object sender, EventArgs e)
        {
            try
            {

                da.DeleteCommand = new SqlCommand("DELETE FROM Song WHERE sid=@s", cs);
                da.DeleteCommand.Parameters.Add("@s", SqlDbType.Int).Value = Int32.Parse(textBox1.Text);


                cs.Open();
                da.DeleteCommand.ExecuteNonQuery();
                MessageBox.Show("Deleted Succesfull from the Database");
                cs.Close();

                textBox1.Text = "";
                textBox2.Text = "";
                textBox3.Text = "";
                textBox4.Text = "";

                da.SelectCommand = new SqlCommand("SELECT * FROM Song", cs);
                ds1.Clear();
                da.Fill(ds1);
                dataGridView2.DataSource = ds1.Tables[0];

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
        }

        //UPDATE
        private void button5_Click(object sender, EventArgs e)
        {
            try
            {
                da.UpdateCommand = new SqlCommand("UPDATE Song set title=@t, length=@l, alid=@a WHERE sid=@s", cs);
                da.UpdateCommand.Parameters.Add("@s", SqlDbType.Int).Value = Int32.Parse(textBox1.Text);
                da.UpdateCommand.Parameters.Add("@t", SqlDbType.NVarChar).Value = textBox2.Text;
                da.UpdateCommand.Parameters.Add("@l", SqlDbType.Real).Value =
               float.Parse(textBox3.Text);
                da.UpdateCommand.Parameters.Add("@a", SqlDbType.Int).Value =
               Int32.Parse(textBox4.Text);

                cs.Open();
                da.UpdateCommand.ExecuteNonQuery();
                MessageBox.Show("Updated Succesfull to the Database");
                cs.Close();

                textBox1.Text = "";
                textBox2.Text = "";
                textBox3.Text = "";
                textBox4.Text = "";

                da.SelectCommand = new SqlCommand("SELECT * FROM Song", cs);
                ds1.Clear();
                da.Fill(ds1);
                dataGridView2.DataSource = ds1.Tables[0];

            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
                cs.Close();
            }
        }
    }
}
